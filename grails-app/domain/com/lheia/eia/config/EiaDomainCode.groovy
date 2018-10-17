package com.lheia.eia.config

class EiaDomainCode {

    /**
     * 代码类型
     */
    String domain
    /**
     * 代码类型描述
     */
    String domainDesc
    /**
     * 代码值
     */
    String code
    /**
     * 代码名称
     */
    String codeDesc
    /**
     * 代码描述
     */
    String codeRemark
    /**
     * 代码级别
     */
    Integer codeLevel = 1
    /**
     * 排序顺序
     */
    Integer displayOrder = 1
    /**
     * 父节点CODE
     */
    String parentCode
    /**
     * 父节点ID
     */
    Long parentId

    Date dateCreated
    Date lastUpdated

    static constraints = {
        domain nullable: true
        domainDesc nullable: true
        code nullable: true
        codeDesc nullable: true
        codeRemark nullable: true
        parentCode nullable: true
        parentId nullable: true
    }
}
