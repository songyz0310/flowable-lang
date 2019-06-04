package com.songyz.flowable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.songyz.flowable.common.BpmnStep;
import com.songyz.flowable.common.FlowableStencil;

/**
 * 流程工具类
 * 
 * @author songyz<br>
 * @createTime 2019-05-29 12:58:44
 */
public class ProcessUtil {

    public static final String KEY_PREFIX = "key";
    public static final String BPMN_SUFFIXES = ".bpmn";
    public static final String USER_TASK = "UserTask";
    public static final String END_EVENT = "EndEvent";

    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    private static final BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
    private static final ObjectMapper objMapper = new ObjectMapper();
    private static final int HIG = 100;// 从一个节点分支出来的节点的最大值

    static {
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
    }

    private static String toJSON(Object obj) {
        try {
            return objMapper.writeValueAsString(obj);
        }
        catch (Exception exp) {
            throw new RuntimeException("toJSON 异常", exp);
        }
    }

    public static String generateKey() {
        return KEY_PREFIX + UUID.randomUUID().toString();
    }

    public static BpmnModel convertToBpmnModelByXml(String bpmnXml) {

        try (InputStream inputStream = new ByteArrayInputStream(bpmnXml.getBytes())) {

            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inputStream);

            return bpmnXMLConverter.convertToBpmnModel(reader);
        }
        catch (Exception exp) {
            throw new RuntimeException("xml 转 bpmn 异常", exp);
        }
    }

    public static BpmnModel convertToBpmnModelByUrl(String url) {

        try (InputStream inputStream = new URL(url).openStream()) {

            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inputStream);

            return bpmnXMLConverter.convertToBpmnModel(reader);

        }
        catch (Exception exp) {
            throw new RuntimeException("url资源 转 bpmn 异常", exp);
        }

    }

    public static InputStream convertToInputStream(BpmnModel bpmnModel) {
        return new ByteArrayInputStream(bpmnXMLConverter.convertToXML(bpmnModel));
    }

    // 使用广度优先原则进行排序
    public static <T extends FlowNode> List<T> sort(Process process, Class<T> type) {

        Map<FlowNode, Integer> sortedMap = new HashMap<>();
        Map<FlowNode, Integer> maxRankMap = new HashMap<>();

        StartEvent startEvent = process.findFlowElementsOfType(StartEvent.class, false).get(0);
        recursionSort(startEvent, 0, sortedMap, maxRankMap);
        List<T> nodeList = sortedMap.entrySet().stream()//
                .filter(entry -> type.isInstance(entry.getKey()))//
                .sorted(Comparator.comparing(entry -> entry.getValue()))//
                .map(entry -> type.cast(entry.getKey()))//
                .collect(Collectors.toList());

        sortedMap.clear();
        maxRankMap.clear();

        List<T> unRankNodeList = process.findFlowElementsOfType(type);
        // 获取未排序节点集合，当个数不一致时，使用未排序节点返回
        if (Objects.equals(nodeList.size(), unRankNodeList.size()) == false) {
            return unRankNodeList;
        }
        else {
            return nodeList;
        }
    }

    // 节点递归排序
    private static void recursionSort(FlowNode node, int rank, Map<FlowNode, Integer> sortedMap,
            Map<FlowNode, Integer> maxRankMap) {
        rank += HIG;

        List<SequenceFlow> incomingFlows = node.getIncomingFlows();
        if (incomingFlows.size() > 1) {

            for (SequenceFlow in : incomingFlows) {
                FlowElement source = in.getSourceFlowElement();
                if (FlowNode.class.isInstance(source)) {

                    // 当已排序的集合中不包含此节点时，证明还有其他分支未递归完，所以结束本分支
                    if (sortedMap.containsKey(source) == false)
                        return;

                    Integer _maxRank = maxRankMap.get(node), _rank = sortedMap.get(source);
                    if (Objects.isNull(_maxRank) || _maxRank < _rank)
                        maxRankMap.put(node, _rank);
                }
            }

            rank = maxRankMap.get(node) + HIG;
        }

        sortedMap.put(node, rank);

        List<SequenceFlow> outgoingFlows = node.getOutgoingFlows();
        for (SequenceFlow out : outgoingFlows) {
            FlowElement target = out.getTargetFlowElement();
            if (Objects.nonNull(target) && FlowNode.class.isInstance(target)
                    && sortedMap.containsKey(target) == false) {
                recursionSort(FlowNode.class.cast(target), rank++, sortedMap, maxRankMap);
            }
        }
    }

    public static Process initNextTask(Process process) {
        process.findFlowElementsOfType(UserTask.class, false).forEach(userTask -> {
            Set<BpmnStep> nextStepSet = new HashSet<>();
            userTask.getOutgoingFlows().forEach(outFlow -> {
                FlowElement targetElement = outFlow.getTargetFlowElement();
                if (FlowNode.class.isInstance(targetElement)) {
                    FlowNode targetNode = FlowNode.class.cast(targetElement);
                    if (EndEvent.class.isInstance(targetNode)) {
                        nextStepSet.add(new BpmnStep(targetNode.getId(), END_EVENT, targetNode.getName()));
                    }
                    else if (UserTask.class.isInstance(targetNode)) {
                        nextStepSet.add(new BpmnStep(targetNode.getId(), USER_TASK, targetNode.getName()));
                    }
                    else if (ExclusiveGateway.class.isInstance(targetNode)) {
                        nextStepSet.addAll(recursionInitNext(ExclusiveGateway.class.cast(targetNode)));
                    }
                }
            });

            // 将结束类型排到任务类型后边
            // 任务类型再使用名称排序
            List<BpmnStep> nextStepList = nextStepSet.stream()
                    .sorted(Comparator.comparing(BpmnStep::getType).reversed().thenComparing(BpmnStep::getStepName))
                    .collect(Collectors.toList());

            ExtensionAttribute attribute = new ExtensionAttribute();
            attribute.setName(FlowableStencil.NEXT_STEPS);
            attribute.setNamespace(FlowableStencil.NAMESPACE);
            attribute.setNamespacePrefix(FlowableStencil.NAMESPACE_PREFIX);
            attribute.setValue(toJSON(nextStepList));
            userTask.addAttribute(attribute);
        });

        return process;

    }

    // 节点递归获取网关后task
    private static Set<BpmnStep> recursionInitNext(ExclusiveGateway exclusiveGateway) {
        Set<BpmnStep> nextNodes = new HashSet<>();

        exclusiveGateway.getOutgoingFlows().forEach(outFlow -> {
            FlowElement targetElement = outFlow.getTargetFlowElement();
            if (FlowNode.class.isInstance(targetElement)) {
                FlowNode targetNode = FlowNode.class.cast(targetElement);
                if (EndEvent.class.isInstance(targetNode)) {
                    nextNodes.add(new BpmnStep(targetNode.getId(), END_EVENT, targetNode.getName()));
                }
                else if (UserTask.class.isInstance(targetNode)) {
                    nextNodes.add(new BpmnStep(targetNode.getId(), USER_TASK, targetNode.getName()));
                }
                else if (ExclusiveGateway.class.isInstance(targetNode)) {
                    nextNodes.addAll(recursionInitNext(ExclusiveGateway.class.cast(targetNode)));
                }
            }
        });

        return nextNodes;
    }

}
