package pareto.core.play;

import pareto.core.entity.Quotation;

import java.util.Iterator;

public class Play extends Thread {

    private final Robot robot;
    private final Iterator<Quotation> quotationIterator;

    private volatile Context context;

    public Play(
            Robot robot,
            Context startContext,
            Iterator<Quotation> quotationIterator
    ) {
        this.robot = robot;
        this.quotationIterator = quotationIterator;
        this.context = startContext;
    }

    @Override
    public void run() {
        while (quotationIterator.hasNext()) {
            Quotation quotation = quotationIterator.next();
            context = context.apply(quotation.getEvents());
            context = context.apply(robot.handle(context));
        }
    }

    public Context getContext() {
        return context;
    }
}
