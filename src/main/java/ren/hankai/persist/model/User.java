
package ren.hankai.persist.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户（后台运维或客户端用户）
 *
 * @author hankai
 * @version 1.0
 * @since Jul 16, 2015 2:00:29 PM
 */
@Entity
@Table(
    name = "users" )
@Cacheable( false )
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY )
    private Integer           id;
    /**
     * 手机号
     */
    @Column(
        length = 45,
        nullable = false )
    @Size(
        min = 1,
        max = 20 )
    @Pattern(
        regexp = "\\d*" )
    private String            mobile;
    /**
     * 登录密码
     */
    @Column(
        length = 100,
        nullable = false )
    @Size(
        min = 1,
        max = 60 )
    private String            password;
    /**
     * 最近一次信息更新的时间
     */
    @Column( )
    @Temporal( TemporalType.DATE )
    private Date              updateTime;
    /**
     * 创建时间
     */
    @Column(
        nullable = false )
    @Temporal( TemporalType.DATE )
    private Date              createTime;
    /**
     * 账号状态
     */
    private UserStatus        status;
    /**
     * 用户角色
     */
    private UserRole          role;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime( Date updateTime ) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime( Date createTime ) {
        this.createTime = createTime;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus( UserStatus status ) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole( UserRole role ) {
        this.role = role;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
