package com.aisino.frems.modules.system.model;

import lombok.Data;
import com.aisino.frems.modules.system.entity.SysDepart;
import com.aisino.frems.modules.system.entity.SysUser;

/**
 * 包含 SysUser 和 SysDepart 的 Model
 *
 * @author sunjianlei
 */
@Data
public class SysUserSysDepartModel {

    private SysUser sysUser;
    private SysDepart sysDepart;

}
