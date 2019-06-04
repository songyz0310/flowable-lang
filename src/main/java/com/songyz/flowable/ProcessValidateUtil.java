package com.songyz.flowable;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorImpl;
import org.flowable.validation.ValidationError;
import org.flowable.validation.validator.ValidatorSet;
import org.flowable.validation.validator.ValidatorSetNames;

import com.songyz.flowable.validator.bpmn.AssociationValidator;
import com.songyz.flowable.validator.bpmn.BoundaryEventValidator;
import com.songyz.flowable.validator.bpmn.BpmnModelValidator;
import com.songyz.flowable.validator.bpmn.DataObjectValidator;
import com.songyz.flowable.validator.bpmn.DiagramInterchangeInfoValidator;
import com.songyz.flowable.validator.bpmn.EndEventValidator;
import com.songyz.flowable.validator.bpmn.ErrorValidator;
import com.songyz.flowable.validator.bpmn.EventGatewayValidator;
import com.songyz.flowable.validator.bpmn.EventSubprocessValidator;
import com.songyz.flowable.validator.bpmn.EventValidator;
import com.songyz.flowable.validator.bpmn.ExecutionListenerValidator;
import com.songyz.flowable.validator.bpmn.FlowElementValidator;
import com.songyz.flowable.validator.bpmn.FlowableEventListenerValidator;
import com.songyz.flowable.validator.bpmn.IntermediateCatchEventValidator;
import com.songyz.flowable.validator.bpmn.IntermediateThrowEventValidator;
import com.songyz.flowable.validator.bpmn.MessageValidator;
import com.songyz.flowable.validator.bpmn.OperationValidator;
import com.songyz.flowable.validator.bpmn.ScriptTaskValidator;
import com.songyz.flowable.validator.bpmn.SendTaskValidator;
import com.songyz.flowable.validator.bpmn.SequenceflowValidator;
import com.songyz.flowable.validator.bpmn.ServiceTaskValidator;
import com.songyz.flowable.validator.bpmn.SignalValidator;
import com.songyz.flowable.validator.bpmn.StartEventValidator;
import com.songyz.flowable.validator.bpmn.SubprocessValidator;
import com.songyz.flowable.validator.bpmn.UserTaskValidator;
import com.songyz.flowable.validator.i1stcs.ManualGatewayValidator;
import com.songyz.flowable.validator.i1stcs.NecessaryNodeParamValidator;
import com.songyz.flowable.validator.i1stcs.NecessaryNodeValidator;
import com.songyz.flowable.validator.i1stcs.NodeAssociationValidator;
import com.songyz.flowable.validator.i1stcs.SequenceValidator;

/**
 * 流程定义校验工具类
 * 
 * @author songyz<br>
 * @createTime 2019-01-05 02:46:23
 */
public class ProcessValidateUtil {

    private static final ProcessValidator processValidator = initI18nProcessValidator();

    public static List<ValidationError> validateProcess(String bpmnXml) throws XMLStreamException {
        return processValidator.validate(ProcessUtil.convertToBpmnModelByXml(bpmnXml));
    }

    private static ProcessValidator initI18nProcessValidator() {
        ProcessValidatorImpl processValidatorImpl = new ProcessValidatorImpl();

        ValidatorSet validatorSet = new ValidatorSet(ValidatorSetNames.FLOWABLE_EXECUTABLE_PROCESS);
        validatorSet.addValidator(new AssociationValidator());
        validatorSet.addValidator(new SignalValidator());
        validatorSet.addValidator(new OperationValidator());
        validatorSet.addValidator(new ErrorValidator());
        validatorSet.addValidator(new DataObjectValidator());

        validatorSet.addValidator(new BpmnModelValidator());
        validatorSet.addValidator(new FlowElementValidator());

        validatorSet.addValidator(new StartEventValidator());
        validatorSet.addValidator(new SequenceflowValidator());
        validatorSet.addValidator(new UserTaskValidator());
        validatorSet.addValidator(new ServiceTaskValidator());
        validatorSet.addValidator(new ScriptTaskValidator());
        validatorSet.addValidator(new SendTaskValidator());
        validatorSet.addValidator(new EventGatewayValidator());
        validatorSet.addValidator(new SubprocessValidator());
        validatorSet.addValidator(new EventSubprocessValidator());
        validatorSet.addValidator(new BoundaryEventValidator());
        validatorSet.addValidator(new IntermediateCatchEventValidator());
        validatorSet.addValidator(new IntermediateThrowEventValidator());
        validatorSet.addValidator(new MessageValidator());
        validatorSet.addValidator(new EventValidator());
        validatorSet.addValidator(new EndEventValidator());

        validatorSet.addValidator(new ExecutionListenerValidator());
        validatorSet.addValidator(new FlowableEventListenerValidator());

        validatorSet.addValidator(new DiagramInterchangeInfoValidator());

        validatorSet.addValidator(new NecessaryNodeValidator());
        validatorSet.addValidator(new NecessaryNodeParamValidator());
        validatorSet.addValidator(new NodeAssociationValidator());
        validatorSet.addValidator(new ManualGatewayValidator());
        validatorSet.addValidator(new SequenceValidator());

        processValidatorImpl.addValidatorSet(validatorSet);
        return processValidatorImpl;
    }

}
