package de.fzi.cep.sepa.declarer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import de.fzi.cep.sepa.desc.declarer.SemanticEventProcessingAgentDeclarer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fzi.cep.sepa.model.impl.Response;
import de.fzi.cep.sepa.model.impl.graph.SepaInvocation;
import de.fzi.cep.sepa.runtime.EPEngine;
import de.fzi.cep.sepa.runtime.EPRuntime;
import de.fzi.cep.sepa.runtime.param.BindingParameters;
import de.fzi.cep.sepa.runtime.param.EngineParameters;

public abstract class EpDeclarer<B extends BindingParameters, EPR extends EPRuntime> implements SemanticEventProcessingAgentDeclarer {

	public static final Logger logger = LoggerFactory.getLogger(EpDeclarer.class.getCanonicalName());
	
	private EPR epRuntime;
	private String elementId;
		
	public void invokeEPRuntime(B bindingParameters, Supplier<EPEngine<B>> supplier, SepaInvocation sepa) throws Exception {
		
		EngineParameters<B> engineParams;
		elementId = sepa.getElementId();
		
		Map<String, Map<String, Object>> inEventTypes = new HashMap<>();
		Map<String, Object> outEventType = new HashMap<>();
		
		outEventType = sepa.getOutputStream().getEventSchema().toRuntimeMap();
		sepa.getInputStreams().forEach(is ->
			inEventTypes.put("topic://" +is.getEventGrounding().getTransportProtocol().getTopicName(), is.getEventSchema().toRuntimeMap()));
		
		engineParams = new EngineParameters<>(
				inEventTypes,
				outEventType, bindingParameters, sepa);

		
		epRuntime = prepareRuntime(bindingParameters, supplier, engineParams);
		epRuntime.initRuntime();
		
		
		start();
	}
		
	public Response detachRuntime(String pipelineId) {
		try {
			preDetach();
			epRuntime.discard();
			return new Response(elementId, true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(elementId, false, e.getMessage());
		}
	}
	
	public abstract void preDetach() throws Exception;
	
	public abstract EPR prepareRuntime(B bindingParameters, Supplier<EPEngine<B>> supplier, EngineParameters<B> engineParams);

	public abstract void start() throws Exception;
}
