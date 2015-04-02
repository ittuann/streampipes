package de.fzi.cep.sepa.actions.samples.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import de.fzi.cep.sepa.actions.messaging.jms.ActiveMQConsumer;
import de.fzi.cep.sepa.actions.messaging.jms.IMessageListener;

public class FileWriter implements Runnable, IMessageListener {

	FileParameters params;
	FileOutputStream stream;
	
	public FileWriter(FileParameters params)
	{
		this.params = params;
		prepare();
	}
	
	private void prepare()
	{
		File file = new File(params.getPath());
		try {
			stream = FileUtils.openOutputStream(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void run() {
		ActiveMQConsumer consumer = new ActiveMQConsumer(params.getUrl(), params.getTopic());
		consumer.setListener(this);
	}

	@Override
	public void onEvent(String json) {
		try {
			stream.write(json.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
