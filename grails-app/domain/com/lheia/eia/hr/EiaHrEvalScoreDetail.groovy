package com.lheia.eia.hr
/**
 * 月考核详情表
 */
class EiaHrEvalScoreDetail {
    /**
     *姓名
     */
    String  staffName
    /**
     * 人员Id
     */
    Long staffId
    /**
     * 考核月份
     */
    String assessmentMonth
    /**
     *工作执行力
     */
    Integer workExecution
    /**
     * 工作业绩
     */
    Integer performance
    /**
     * 工作技能
     */
    Integer jobSkill
    /**
     *工作态度
     */
    Integer workingAttitude
    /**
     * 团队精神
     */
    Integer teamSpirit
    /**
     * 企业文化认知
     */
    Integer  cultureCognition
    /**
     * 领导能力
     */
    Integer  leadership
    /**
     * 专业能力
     */
    Integer proAbility
    /**
     * 类型
     */
    String jobRatingType
    /**
     * 角色名称
     */
    String roleName
    /**
     * 角色编码
     */
    String roleCode
    /**
     * 是否提交
     */
    Boolean ifSubmit = false
    /**
     * 领导评语
     */
    String  leaderComments
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门
     */
    String inputDeptCode
    /**
     * 录入部门ID
     */
    Long inputDeptId
    /**
     * 录入人
     */
    String inputUser
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 是否删除
     */
    Boolean ifDel = false
    /**
     * 创建日期
     */
    Date dateCreated
    /**
     * 更新日期
     */
    Date lastUpdated
    static mapping = {
        leaderComments column: "leader_comments", sqlType: "LONGTEXT"
    }
    static constraints = {
        staffName nullable:true
        ifDel nullable:true
        assessmentMonth nullable:true
        workExecution nullable:true
        performance nullable:true
        jobSkill nullable:true
        workingAttitude nullable:true
        teamSpirit nullable:true
        cultureCognition nullable:true
        inputDept nullable:true
        inputDeptId nullable:true
        inputUser nullable:true
        inputUserId nullable:true
        leadership nullable: true
        proAbility nullable: true
        roleName nullable: true
        roleCode nullable: true
        jobRatingType nullable: true
        leaderComments nullable: true
        ifSubmit nullable: true
    }
}
