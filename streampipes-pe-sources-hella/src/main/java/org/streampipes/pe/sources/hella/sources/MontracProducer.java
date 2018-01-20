package org.streampipes.pe.sources.hella.sources;

import java.util.ArrayList;
import java.util.List;

import org.streampipes.container.declarer.DataStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.pe.sources.hella.streams.MaterialMovementStream;

public class MontracProducer implements SemanticEventProducerDeclarer {

	@Override
	public DataSourceDescription declareModel() {
		
		DataSourceDescription sep = new DataSourceDescription("source-montrac", "Montrac", "Provides streams generated by the Montrac transportation system");
		
		return sep;
	}

	
	@Override
	public List<DataStreamDeclarer> getEventStreams() {
		
		List<DataStreamDeclarer> streams = new ArrayList<DataStreamDeclarer>();
		
		streams.add(new MaterialMovementStream());
		
		return streams;
	}
}
