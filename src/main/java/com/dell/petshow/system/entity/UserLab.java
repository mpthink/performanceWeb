package com.dell.petshow.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;


/**
 * <p>
 * 用户lab表
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public class UserLab extends Model<UserLab> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * Lab ID
     */
	private Long labId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	@Override
	public String toString(){
		return "UserLab [id=" + id + ", userId=" + userId + ", labId=" + labId + "]";
	}
}
