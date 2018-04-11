package org.hhs.hush.netty.test;

import java.util.ArrayList;
import java.util.List;

public class ProcessChain implements Processor {
    private List<Processor> processorList = new ArrayList();
    private int index = 0;

    public ProcessChain addProcesser(Processor p){
        processorList.add(p);
        return this;
    }

    @Override
    public void handler(Request request, Response response, ProcessChain p) {
        if (index == processorList.size()){
            return;
        }
        Processor temp = processorList.get(index);
        index++;
        temp.handler(request, response, p);
    }
}
