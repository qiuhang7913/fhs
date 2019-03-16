package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.nosql.redis.ObjectRedisService;
import com.self.framework.otherBean.TreeNode;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.dao.MenuDao;
import com.self.framework.ucenter.service.MenuService;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends BaseServiceImpl<SysMenuResource> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ObjectRedisService<List<TreeNode>> redisService;

    @Override
    public Integer addOrUpdata(SysMenuResource v) {
        SysMenuResource sysMenuResource = findOne(v);
        if (ObjectCheckUtil.checkIsNullOrEmpty(sysMenuResource)) {//添加操作
            Example<SysMenuResource> ofRes = Example.of(SysMenuResource.builder().id(v.getParentId()).build());
            SysMenuResource sysMenuResourceParent = menuDao.findOne(ofRes).get();
            if (!ObjectCheckUtil.checkIsNullOrEmpty(sysMenuResourceParent)) {
                menuDao.save(sysMenuResourceParent);
            }
        }
        return super.addOrUpdata(v);
    }

    @Override
    public List<TreeNode> findMenuTreeData() {
        TreeNode treeNode = new TreeNode();
        treeNode.setTreeId(BusinessCommonConstamt.ZERO_STRING_CODE);

        //缓存处理
        if (redisService.isExist(MENU_TREENODES_CACHE_CODE)){
            return redisService.getObj(MENU_TREENODES_CACHE_CODE);
        }else{
            List<TreeNode> treeNodes = getTreeNodeSon(treeNode);
            redisService.insertObj(MENU_TREENODES_CACHE_CODE, 3600L, treeNodes);
            return treeNodes;
        }
    }

    /**
     * 回调儿子查询
     * @param root
     * @return
     */
    private List<TreeNode> getTreeNodeSon(TreeNode root){
        String sql = "SELECT m.name, m.id, m.parent_id as parentId, m.url FROM menu m WHERE m.parent_id = :parentId ORDER BY sort ASC";
        List<Map<String,Object>> queryDatas = menuDao.findOther(sql, new HashMap<String, Object>() {{
            put("parentId", root.getTreeId());
        }});
        List<TreeNode> treeNodes = new ArrayList<>();
        queryDatas.forEach(data -> {
            TreeNode treeNode = new TreeNode(
                    ConvertDataUtil.convertStr(data.get("name")),
                    ConvertDataUtil.convertStr(data.get("id")),
                    ConvertDataUtil.convertStr(data.get("parentId")),
                    ConvertDataUtil.convertStr("#")
            );
            treeNodes.add(treeNode);
        });

        //存在下一级
        if(!ObjectCheckUtil.checkIsNullOrEmpty(queryDatas)){
            treeNodes.forEach( treeNode -> {
                treeNode.setNodes(getTreeNodeSon(treeNode));
            });
        }

        return treeNodes;
    }
}
