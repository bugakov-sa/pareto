package pareto.core.api.dto.mapper;

import pareto.core.api.dto.*;
import pareto.core.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtil {

    public static RobotDto map(Robot robot) {
        RobotDto res = new RobotDto();
        res.setId(robot.getId());
        res.setClassName(robot.getClassName());
        res.setParams(robot.getParams().stream().map(MappingUtil::map).collect(Collectors.toList()));
        return res;
    }

    public static List<Param> map(List<ParamDto> paramDtos) {
        return paramDtos.stream().map(paramDto -> {
            Param param = new Param();
            param.setName(paramDto.getName());
            param.setValue(paramDto.getValue());
            return param;
        }).collect(Collectors.toList());
    }

    public static ParamDto map(Param param) {
        ParamDto res = new ParamDto();
        res.setName(param.getName());
        res.setValue(param.getValue());
        return res;
    }

    public static ContextDto map(Context context) {
        ContextDto res = new ContextDto();
        res.setId(context.getId());
        res.setName(context.getName());
        res.setDescription(context.getDescription());
        res.setParams(context.getParams().stream().map(MappingUtil::map).collect(Collectors.toList()));
        return res;
    }

    public static List<ContextDto> mapContexts(List<Context> contexts) {
        return contexts.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static List<RobotDto> mapRobots(List<Robot> robots) {
        return robots.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static PlayDto map(Play play) {
        PlayDto res = new PlayDto();
        res.setId(play.getId());
        res.setRobotId(play.getRobotId());
        res.setContextId(play.getContextId());
        res.setStatus(play.getStatus());
        return res;
    }

    public static List<PlayDto> mapPlays(List<Play> plays) {
        return plays.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static ProductDto map(Product product) {
        ProductDto res = new ProductDto();
        res.setId(product.getId());
        res.setName(product.getName());
        return res;
    }

    public static List<ProductDto> mapProducts(List<Product> products) {
        return products.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static QuotationDto map(Quotation quotation) {
        QuotationDto res = new QuotationDto();
        res.setProductId(quotation.getProductId());
        res.setTime(quotation.getTime());
        res.setOpen(quotation.getOpen());
        res.setClose(quotation.getClose());
        res.setMin(quotation.getMin());
        res.setMax(quotation.getMax());
        return res;
    }

    public static Quotation map(QuotationDto quotation) {
        Quotation res = new Quotation();
        res.setProductId(quotation.getProductId());
        res.setTime(quotation.getTime());
        res.setOpen(quotation.getOpen());
        res.setClose(quotation.getClose());
        res.setMin(quotation.getMin());
        res.setMax(quotation.getMax());
        return res;
    }

    public static List<QuotationDto> mapQuotations(List<Quotation> quotations) {
        return quotations.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static List<Quotation> mapQuotationDtos(List<QuotationDto> quotations) {
        return quotations.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static PlayPnlDto map(PlayPnl playPnl) {
        PlayPnlDto res = new PlayPnlDto();
        res.setTime(playPnl.getTime());
        res.setOpen(playPnl.getOpen());
        res.setClose(playPnl.getClose());
        res.setMin(playPnl.getMin());
        res.setMax(playPnl.getMax());
        return res;
    }

    public static List<PlayPnlDto> mapPlayPnls(List<PlayPnl> playPnls) {
        return playPnls.stream().map(MappingUtil::map).collect(Collectors.toList());
    }

    public static EventDto map(Event event) {
        EventDto res = new EventDto();
        res.setId(event.getId());
        res.setPlayId(event.getPlayId());
        res.setTime(event.getTime());
        res.setEventType(event.getEventType());
        res.setParams(event.getParams().stream().map(MappingUtil::map).collect(Collectors.toList()));
        return res;
    }

    public static List<EventDto> mapEvents(List<Event> events) {
        return events.stream().map(MappingUtil::map).collect(Collectors.toList());
    }
}
