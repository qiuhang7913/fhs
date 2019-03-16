package com.self.framework.otherBean;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @des 树形数据封装bean
 * @author qh
 * @version v1.0
 */
@Data
@ToString
public class TreeNode implements Serializable {

    /** 列表树节点上的文本 */
    private String text;

    /** 列表树节点上的标识 */
    private String treeId;

    /** 父标识 */
    private String treeParentId;

    /** 结合全局enableLinks选项为列表树节点指定URL */
    private String href;

    /** 子集 */
    private List<TreeNode> nodes;

    public TreeNode(){

    }

    public TreeNode(String text, String treeId, String treeParentId, String href){
        this.text = text;
        this.treeId = treeId;
        this.treeParentId = treeParentId;
        this.href = href;
        this.nodes = new ArrayList<>();
    }
}
