package pareto.core.api.dto.mapper;

import pareto.core.api.dto.*;
import pareto.core.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingUtil {

    public static AlgorithmDto map(AlgorithmMeta algorithmMeta) {
        AlgorithmDto res = new AlgorithmDto();
        res.setId(algorithmMeta.getId());
        res.setName(algorithmMeta.getName());
        res.setDescription(algorithmMeta.getDescription());
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

    public static RobotDto map(RobotMeta robotMeta) {
        RobotDto res = new RobotDto();
        res.setId(robotMeta.getId());
        res.setAlgorithm(map(robotMeta.getAlgorithmMeta()));
        res.setParams(map(robotMeta.getParams()));
        return res;
    }

    public static ContextDto map(ContextMeta contextMeta) {
        ContextDto res = new ContextDto();
        res.setId(contextMeta.getId());
        res.setName(contextMeta.getName());
        res.setDescription(contextMeta.getDescription());
        res.setParams(map(contextMeta.getParams()));
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
        res.setRobot(map(playMeta.getRobotMeta()));
        res.setContext(map(playMeta.getContextMeta()));
        res.setStatus(map(playMeta.getStatus()));
        return res;
    }

    public static Map<String, String> map(List<ParamDto> paramDtos) {
        Map<String, String> res = new HashMap<>();
        paramDtos.forEach(paramDto -> res.put(paramDto.getName(), paramDto.getValue()));
        return res;
    }

    public static ContextMeta map(NewContextDto newContextDto) {
        return new ContextMeta(
                0,
                newContextDto.getName(),
                newContextDto.getDescription(),
                map(newContextDto.getParams())
        );
    }
}
