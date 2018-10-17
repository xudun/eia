package com.lheia.eia.cert
/***
 * 资质
 */
class EiaCert {
    /**
     * 任务外键ID
     */
    Long eiaTaskId
    /**
     * 项目id
     */
    Long eiaProjectId
    /**
     * 客户ID
     */
    Long eiaClientId
    /**
     * 客户名称
     */
    String eiaClientName
    /**
     * 合同id
     */
    Long eiaContractId
    /**
     * 合同名称
     */
    String contractName
    /**
     * 合同编号
     */
    String contractNo
    /**
     * 主管部门
     */
    String competentDept
    /**
     * 项目类型（环保咨询，节能评估, 绿色金融）
     */
    String projectType
    /**
     * 项目名称
     */
    String projectName
    /**
     * 项目编号
     */
    String projectNo
    /**
     * 申请部门
     */
    String inputDept
    /**
     * 申请部门ID
     */
    Long inputDeptId
    /**
     * 申请部门Code
     */
    String inputDeptCode
    /**
     * 申请人
     */
    String inputUser
    /**
     * 申请人ID
     */
    Long inputUserId
    /**
     * 申请日期
     */
    Date applyDate
    /**
     * 资质类型报批approval 送审review
     */
    String certType
    /**
     * 资质申请是否结束
     */
    boolean ifEnd = false
    /**
     * 是否申请资质
     */
    boolean ifApplyCert
    /**
     * 打印资质数量
     */
    Integer certNum
    /**
     * 是否报告盖章
     */
    boolean ifReportSeal
    /**
     * 是否发放资质
     */
    boolean ifSendCert
    /**
     * 开会时间
     */
    Date meetDate
    /**
     * 是否删除
     */
    boolean ifDel =false
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    /**
     * 是否提交
     */
    Boolean ifSub = false
    /**
     * 是否提交
     */
    long parentEiaCertId
    /**
     * 印章类型
     */
    String stampType
    /**
     * 特殊说明
     */
    String stampNote

    static mapping = {
        eiaTaskId index: "idx_eia_task_id"
        eiaProjectId index: "idx_eia_project_id"
        eiaContractId index: "idx_eia_contract_id"
    }

    static constraints = {
        eiaTaskId nullable: true
        eiaProjectId nullable: true
        contractNo nullable:true
        eiaClientId nullable: true
        eiaClientName nullable: true
        eiaContractId nullable: true
        inputDeptCode nullable:true
        contractName nullable: true
        competentDept nullable: true
        projectType nullable: true
        projectName nullable: true
        projectNo nullable: true
        inputDept nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        inputDeptId nullable: true
        applyDate nullable: true
        ifApplyCert nullable: true
        ifSendCert nullable: true
        ifReportSeal nullable: true
        ifDel nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
        ifSub nullable: true
        certNum nullable: true
        certType nullable: true
        ifEnd nullable:true
        parentEiaCertId nullable:true
        stampType nullable:true
        stampNote nullable:true
        meetDate nullable:true
    }
}
