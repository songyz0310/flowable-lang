package com.songyz.flowable.cmd;

import java.util.List;

import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;

import com.songyz.flowable.ProcessUtil;

/**
 * 使用深度优先原则进行节点排序<br>
 * 注排序前，先对节点的下一级进行解析，
 * 
 * @author songyz
 * @createTime 2019-01-25 01:36:53
 */
public class GetSortedNodeCmd<T extends FlowNode> implements Command<List<T>> {

    protected Class<T> type;
    protected String processDefinitionId;

    public GetSortedNodeCmd(String processDefinitionId, Class<T> type) {
        this.processDefinitionId = processDefinitionId;
        this.type = type;
    }

    @Override
    public List<T> execute(CommandContext commandContext) {
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);

        ProcessUtil.initNextTask(process);
        ProcessUtil.initNextGateway(process);

        return ProcessUtil.sort(process, type);
    }

}
