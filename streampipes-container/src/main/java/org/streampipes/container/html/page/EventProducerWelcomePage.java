package org.streampipes.container.html.page;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.streampipes.container.declarer.DataStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.container.html.model.AgentDescription;
import org.streampipes.container.html.model.Description;
import org.streampipes.container.html.model.DataSourceDescriptionHtml;

@Deprecated
public class EventProducerWelcomePage extends WelcomePageGenerator<SemanticEventProducerDeclarer> {

	public EventProducerWelcomePage(String baseUri, List<SemanticEventProducerDeclarer> declarers)
	{
		super(baseUri, declarers);
	}
	
	@Override
	public List<Description> buildUris()
	{
		for(SemanticEventProducerDeclarer declarer : declarers)
		{
			List<Description> streams = new ArrayList<Description>();
			DataSourceDescriptionHtml description = new DataSourceDescriptionHtml();
			description.setName(declarer.declareModel().getName());
			description.setDescription(declarer.declareModel().getDescription());
			description.setUri(URI.create(baseUri + declarer.declareModel().getUri()));
			for(DataStreamDeclarer streamDeclarer : declarer.getEventStreams())
			{
				AgentDescription ad = new AgentDescription();
				ad.setDescription(streamDeclarer.declareModel(declarer.declareModel()).getDescription());
				ad.setUri(URI.create(baseUri + streamDeclarer.declareModel(declarer.declareModel()).getUri()));
				ad.setName(streamDeclarer.declareModel(declarer.declareModel()).getName());
				streams.add(ad);
			}
			description.setStreams(streams);
			descriptions.add(description);
		}
		return descriptions;
	}
	
}
