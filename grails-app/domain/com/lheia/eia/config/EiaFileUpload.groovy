package com.lheia.eia.config

class EiaFileUpload {

    Long tableId
    String tableName
    /**上传文件名**/
    String fileName
    /**文件保存名**/
    String saveFileName
    /**文件Ip地址**/
    String fileIp
    /**文件路径**/
    String filePath
    /**文件大小**/
    Long fileSize
    /**文件类型(txt、jpg等)**/
    String fileType
    /**文件上传类型(合同、名片等)**/
    String fileUploadType
    /**
     * 是否可以删除
     */
    boolean ifReadOnly = true
    String fileNote
    String inputDept
    Long inputDeptId
    Long inputUserId
    String inputUser
    String inputDeptCode
    Boolean ifDel = false
    Date dateCreated
    Date lastUpdated
    static mapping = {
        fileNote column: "file_note", sqlType: "LONGTEXT"
    }
    static constraints = {
        tableId nullable: true
        tableName nullable: true
        saveFileName nullable: true
        fileType nullable: true
        fileUploadType nullable: true
        filePath nullable: true
        fileIp nullable: true
        fileSize nullable: true
        fileNote nullable: true
        inputDeptId nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        ifReadOnly nullable:true
    }
}
