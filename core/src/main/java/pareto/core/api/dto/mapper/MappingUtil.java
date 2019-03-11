package pareto.core.api.dto.mapper;

import pareto.core.api.dto.*;
import pareto.core.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MappingUtil {

    public static AlgorithmDto map(Algorithm algorithm) {
        AlgorithmDto res = new AlgorithmDto();
        res.setId(algorithm.getId());
        res.setName(algorithm.getName());
        res.setDescription(algorithm.getDescription());
        return res;
    }

    public static List<ParamDto> map(Map<String, String> params) {
        List<ParamDto> res = new ArrayList<>();
        params.forEach((name, value) -> {
            ParamDto paramDto = new ParamDto();
            paramDto.setName(name);
            paramDto.setValue(value);
            res.add(paramDto);
        });
        return res;
    }

    public static RobotDto map(Robot robot) {
        RobotDto res = new RobotDto();
        res.setId(robot.getId());
        res.setAlgorithm(map(robot.getAlgorithm()));
        res.setParams(map(robot.getParams()));
        return res;
    }

    public static String map(PlayStatus playStatus) {
        switch (playStatus) {
            case CREATED:
                return "created";
            case PLAYING:
                return "playing";
            default:
                return "stopped";
        }
    }

    public static PlayDto map(PlayMeta playMeta) {
        PlayDto res = new PlayDto();
        res.setId(playMeta.getId());
        res.setRobot(map(playMeta.getRobot()));
        res.setContext(map(playMeta.getContext()));
        res.setStatus(map(playMeta.getStatus()));
        return res;
    }

    public static List<ContextParam> map(List<ParamDto> paramDtos) {
        return paramDtos.stream().map(paramDto -> {
            ContextParam contextParam = new ContextParam();
            contextParam.setName(paramDto.getName());
            contextParam.setValue(paramDto.getValue());
            return contextParam;
        }).collect(Collectors.toList());
    }

    public static ParamDto map(ContextParam contextParam) {
        ParamDto res = new ParamDto();
        res.setName(contextParam.getName());
        res.setValue(contextParam.getValue());
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

    public static List<ContextDto> mapContextMetas(List<Context> contexts) {
        return contexts.stream().map(MappingUtil::map).collect(Collectors.toList());
    }
}
