package com.zero.base.pojo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tree implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String iconCls;
	private String parentId;
	private String checked;
	private String state;
	private Map<String, Object> attributes;
	private List<Tree> children;
	private String isleaf;
	private String val;
	private HashMap<String, String> attrs;

	@JsonIgnore
	private String dataSecurity;

	public String getDataSecurity() {
		return dataSecurity;
	}

	public void setDataSecurity(String dataSecurity) {
		this.dataSecurity = dataSecurity;
	}

	public HashMap<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(HashMap<String, String> attrs) {
		this.attrs = attrs;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void addAttribute(String key, String value) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}

		this.attributes.put(key, value);
	}

	public void addChild(Tree child) {
		if (this.children == null) {
			this.children = new ArrayList<Tree>();
		}

		this.children.add(child);
	}

	/**
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}
