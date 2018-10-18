package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.project.EiaEneProject
import com.lheia.eia.project.EiaEnvProject
import com.lheia.eia.project.EiaGreenProject
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON

class EiaWorkFlowProjectController {
    def eiaWorkFlowProjectService
    def eiaFileUploadService
    def eiaPlatFormService
    /**
     * 项目通用下一个节点
     */
    def nextWorkFlowNode() {
        def processUrlParams = params.processUrlParams
        def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(params.processUrlParams, params)
        if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
            render(beforeNextNode as JSON)
        } else {
            if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                /**——————处理完毕后，进入下一个工作流———————**/
                def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                        , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                if (nextNode) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 提交到现场勘察的工作流
     */
    def nextFlowNodeXckc() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_XCKC) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_XCKC, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到编制完成的工作流
     */
    def nextFlowNodeBzwc() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_BZWC) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {

            /** 判断项目金额 */
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findById(Long.valueOf(params.eiaWorkFlowBusiId))
            def eiaProjectId = eiaWorkFlowBusi?.tableNameId
            def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
            if (eiaProject.projectTypeCode != 'GREEN') {
                def eiaEnvProject = EiaEnvProject.findByEiaProjectId(eiaProjectId)
                def eiaEneProject = EiaEneProject.findByEiaProjectId(eiaProjectId)
                def eiaGreenProject = EiaGreenProject.findByEiaProjectId(eiaProjectId)
                if (!eiaEnvProject && !eiaEneProject && !eiaGreenProject) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PROJECT_EXPLORE_IS_NULL] as JSON)
                    return
                }
            }
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_BZWC, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到检测方案
     */
    def nextFlowNodeJcfa() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_JCFA) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_JCFA, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }

    /**
     * 提交到一审的工作流
     */
    def nextFlowNodeYs() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_YS) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            /** 判断项目金额 */
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findById(Long.valueOf(params.eiaWorkFlowBusiId))
            def eiaProject = EiaProject.findById(eiaWorkFlowBusi?.tableNameId)
            if (!eiaProject?.projectMoney) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PROJECT_MONEY_IS_NULL] as JSON)
                return
            }
            if (params.nodeUserName == eiaWorkFlowBusi.inputUser) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_COMMIT_SELF] as JSON)
                return
            }
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_YS, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }

    /**
     * 提交到一审编制的工作流
     */
    def nextFlowNodeYsbz() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_YSBZ) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_YSBZ, params)
            if (beforeNextNode.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到二审的工作流
     */
    def nextFlowNodeEs() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_ES) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            if (params.nodeUserName == session.staff.staffName) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_COMMIT_SELF] as JSON)
                return
            }
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_ES, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode
                    if (params.modiContent) {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams, params.modiContent, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    } else {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams, params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    }
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到二审编制
     */
    def nextFlowNodeEsbz() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_ESBZ) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_ESBZ, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到三审的工作流
     */
    def nextFlowNodeSs() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_SS) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            if (params.nodeUserName == session.staff.staffName) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_COMMIT_SELF] as JSON)
                return
            }
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_SS, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode
                    if (params.modiContent) {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.modiContent, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    } else {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    }
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到三审编制的工作流
     */
    def nextFlowNodeSsbz() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_SSBZ) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_SSBZ, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到轮审的工作流
     */
    def nextFlowNodeLs() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_LS) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {

            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_LS, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode
                    if (params.modiContent) {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.modiContent, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    } else {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    }
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到轮审编制的工作流
     */
    def nextFlowNodeLsbz() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_LSBZ) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_LSBZ, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到技术评审会的工作流
     */
    def nextFlowNodeJspsh() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_JSPSH) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_JSPSH, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode
                    if (params.modiContent) {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.modiContent, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    } else {
                        nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                                , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    }
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到报批版上报的工作流
     */
    def nextFlowNodeBpbsb() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_BPBSB) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_BPBSB, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                /**——————————保存项目中的合同————————————**/

                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到项目归档
     */
    def nextFlowNodeXmgd() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_XMGD) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_XMGD, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                            , params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 提交到流程结束
     */
    def nextFlowNodeEnd() {
        def processUrlParams = params.processUrlParams
        if (processUrlParams != WorkFlowConstants.NODE_CODE_END) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        } else {
            def beforeNextNode = eiaWorkFlowProjectService.beforeNextNode(WorkFlowConstants.NODE_CODE_END, params)
            if (beforeNextNode?.code == HttpMesConstants.CODE_FAIL) {
                render(beforeNextNode as JSON)
            } else {
                if (beforeNextNode && beforeNextNode != HttpMesConstants.MSG_SAVE_FAIL) {
                    /**——————处理完毕后，进入下一个工作流———————**/
                    def nextNode = eiaWorkFlowProjectService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams, params.opinion, params.processCode, session, params.nodeUserName, params.long('nodeUserId'))
                    eiaFileUploadService.UpdateFileReadOnly(nextNode.tableName, nextNode.tableNameId)
                    /** 归档后的项目保存至共享平台 */
                    eiaPlatFormService.dataShareSaveEiaProject(nextNode.tableNameId)
                    if (nextNode) {
                        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }

    /**
     * 获取流程节点的审批意见
     */
    def getNodeOpinion() {
        def nodeOpion = eiaWorkFlowProjectService.getNodeOpinion(params)
        if (nodeOpion) {
            render([code: HttpMesConstants.CODE_OK, data: nodeOpion] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 流程终止
     */
    def workFlowHalt() {
        def resMap = eiaWorkFlowProjectService.workFlowHalt(Long.valueOf(params.eiaWorkFlowBusiId), params.opinion, Long.valueOf(params.version), session)
        if (resMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_FLOW_HALT_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    /**
     * 检查当前节点是否存在与工作流中
     * @return
     */
    def checkNodeExist() {
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        def workFlowNodeDic = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson).workFlowNodeDic
        boolean inDic
        workFlowNodeDic.each { key, value ->
            inDic |= (key == params.processUrlParams)
        }
        if (params.processUrlParams == WorkFlowConstants.NODE_CODE_END || params.processUrlParams == WorkFlowConstants.NODE_CODE_LCZZ) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else if (!inDic) {
            render([code: HttpMesConstants.CODE_FAIL, msg: "没有下一个节点"] as JSON)
        } else {
            def projectIfLab = EiaProject.findByIdAndIfDel(eiaWorkFlowBusi.tableNameId, false).ifLab
            if (projectIfLab) {
                /**————编制完成到一审检查是否有检测方案——————**/
                if ((params.nodesCode == WorkFlowConstants.NODE_CODE_BZWC && params.processUrlParams == WorkFlowConstants.NODE_CODE_YS) || (params.nodesCode == WorkFlowConstants.NODE_CODE_BZWC && params.processUrlParams == WorkFlowConstants.NODE_CODE_ES)) {
                    def eiaLabOffer = EiaLabOffer.findByEiaProjectIdAndIfDel(eiaWorkFlowBusi.tableNameId, false)
                    if (!eiaLabOffer) {
                        render([code: HttpMesConstants.CODE_FAIL, msg: "无检测方案，不能提交"] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_OK] as JSON)
                    }
                }
            }
            render([code: HttpMesConstants.CODE_OK] as JSON)
        }
    }

}
