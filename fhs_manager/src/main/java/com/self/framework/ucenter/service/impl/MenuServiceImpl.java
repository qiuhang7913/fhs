package com.self.framework.ucenter.service.impl;

import com.self.framework.base.BaseServiceImpl;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.otherBean.TreeNode;
import com.self.framework.ucenter.bean.SysMenuResource;
import com.self.framework.ucenter.dao.MenuDao;
import com.self.framework.ucenter.service.MenuService;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends BaseServiceImpl<SysMenuResource> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<TreeNode> findMenuTreeData() {
        TreeNode treeNode = new TreeNode();
        treeNode.setTreeId(BusinessCommonConstamt.ZERO_STRING_CODE);
        return getTreeNodeSon(treeNode);
    }

    /**
     * 回调儿子查询
     * @param root
     * @return
     */
    private List<TreeNode> getTreeNodeSon(TreeNode root){
        String sql = "SELECT m.name,m.id,m.url FROM menu m WHERE m.parent_id = :parentId ORDER BY sort ASC";
        List<Map<String,Object>> queryDatas = menuDao.findOther(sql, new HashMap<String, Object>() {{
            put("parentId", root.getTreeId());
        }});
        List<TreeNode> treeNodes = new ArrayList<>();
        queryDatas.forEach(data -> {
            TreeNode treeNode = new TreeNode(
                    ConvertDataUtil.convertStr(data.get("name")),
                    ConvertDataUtil.convertStr(data.get("id")),
                    ConvertDataUtil.convertStr(data.get("url"))
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
