package com.lheia.eia.hr
/**
 * 月考核最终得分
 */

class EiaHrEvalScore {
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
     * 领导能力(部门经理)
     */
    Integer  leadershipManager
    /**
     * 专业能力
     */
    Integer proAbility
    /**
     * 类型
     */
    String jobRatingType
    /**
     * 最终得分
     */
    Integer finalScore
    /**
     * 领导评语
     */
    String  leaderComments
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门Code
     */
    String inputDeptCode
    /**
     * 录入部门ID
     */
    Long inputDeptId
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
        leadership nullable: true
        proAbility nullable: true
        jobRatingType nullable: true
        finalScore nullable: true
        leadershipManager nullable: true
        leaderComments nullable: true
        inputDeptCode nullable:true

    }
}
