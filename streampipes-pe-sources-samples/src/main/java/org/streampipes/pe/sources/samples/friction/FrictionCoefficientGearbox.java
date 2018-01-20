package org.streampipes.pe.sources.samples.friction;

import org.streampipes.model.SpDataStream;
import org.streampipes.model.graph.DataSourceDescription;
import org.streampipes.container.declarer.DataStreamDeclarer;

public class FrictionCoefficientGearbox extends FrictionCoefficient implements DataStreamDeclarer {

    protected FrictionCoefficientGearbox() {
        super(FrictionVariable.Gearbox);
    }

    @Override
    public SpDataStream declareModel(DataSourceDescription sep) {
        return prepareStream(sep);
    }

    @Override
    public void executeStream() {

    }

    @Override
    public boolean isExecutable() {
        return false;
    }
}
