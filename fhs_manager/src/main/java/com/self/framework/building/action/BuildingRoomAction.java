package com.self.framework.building.action;

import com.self.framework.annotation.SysLog;
import com.self.framework.base.BaseAction;
import com.self.framework.building.bean.BuildingRoomBean;
import com.self.framework.building.service.BuildingRoomService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/building/room")
@SysLog
public class BuildingRoomAction extends BaseAction<BuildingRoomBean> {

    @Autowired
    private BuildingRoomService buildingRoomService;

    @RequestMapping(value = "checkRoomCode")
    @ResponseBody
    public Map<String, Boolean> checkRoomCode(
            @RequestParam(value = "room_code", required = false) String roomCode){
        Map<String,Boolean> rvMap = new HashMap<>();

        BuildingRoomBean one = buildingRoomService.findOne(BuildingRoomBean.builder().roomCode(roomCode).build());
        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            rvMap.put("valid", true);
        }else{
            rvMap.put("valid", false);
        }
        return rvMap;
    }
}
