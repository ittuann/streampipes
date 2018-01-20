package org.streampipes.pe.sources.hella.sources;

import java.util.Arrays;
import java.util.List;

import org.streampipes.container.declarer.DataStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.pe.sources.hella.streams.MouldingParameterStream;

public class MouldingMachineProducer implements SemanticEventProducerDeclarer {

	@Override
	public DataSourceDescription declareModel() {
		DataSourceDescription sep = new DataSourceDescription("source-moulding", "Moulding Machine", "Provides streams generated by several moulding machines");
		
		return sep;
	}

	@Override
	public List<DataStreamDeclarer> getEventStreams() {
		return Arrays.asList(new MouldingParameterStream());
	}

}
