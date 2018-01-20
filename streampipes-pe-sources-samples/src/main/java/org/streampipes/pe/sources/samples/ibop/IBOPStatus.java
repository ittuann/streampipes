package org.streampipes.pe.sources.samples.ibop;

import java.util.ArrayList;
import java.util.List;

import org.streampipes.vocabulary.MessageFormat;
import org.streampipes.vocabulary.XSD;
import org.streampipes.container.declarer.DataStreamDeclarer;
import org.streampipes.model.grounding.EventGrounding;
import org.streampipes.model.schema.EventProperty;
import org.streampipes.model.schema.EventPropertyPrimitive;
import org.streampipes.model.schema.EventSchema;
import org.streampipes.model.SpDataStream;
import org.streampipes.model.grounding.TransportFormat;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.pe.sources.samples.config.AkerVariables;
import org.streampipes.pe.sources.samples.config.ProaSenseSettings;
import org.streampipes.pe.sources.samples.config.SourcesConfig;
import org.streampipes.commons.Utils;

public class IBOPStatus implements DataStreamDeclarer {

	
	@Override
	public SpDataStream declareModel(DataSourceDescription sep) {
		
		SpDataStream stream = new SpDataStream();
		
		EventSchema schema = new EventSchema();
		List<EventProperty> eventProperties = new ArrayList<EventProperty>();
		eventProperties.add(new EventPropertyPrimitive(XSD._long.toString(), "variable_type", "", Utils.createURI("http://schema.org/Number")));
		eventProperties.add(new EventPropertyPrimitive(XSD._string.toString(), "variable_timestamp", "", Utils.createURI("http://schema.org/DateTime")));
		eventProperties.add(new EventPropertyPrimitive(XSD._double.toString(), "variable_value", "", Utils.createURI("http://sepa.event-processing.org/sepa#ibopStatus")));
		
		
		EventGrounding grounding = new EventGrounding();
		grounding.setTransportProtocol(ProaSenseSettings.standardProtocol(AkerVariables.Ibop.topic()));
		grounding.setTransportFormats(Utils.createList(new TransportFormat(MessageFormat.Json)));

		stream.setEventGrounding(grounding);
		schema.setEventProperties(eventProperties);
		stream.setEventSchema(schema);
		stream.setName(AkerVariables.Ibop.eventName());
		stream.setDescription(AkerVariables.Ibop.description());
		stream.setUri(sep.getUri() + "/torque");
		stream.setIconUrl(SourcesConfig.iconBaseUrl + "/Temperature_Icon" +"_HQ.png");
		
		return stream;
	}

	@Override
	public void executeStream() {
	}

	@Override
	public boolean isExecutable() {
		return false;
	}
}
