(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3813f99d"],{"4fad":function(t,e,o){var n=o("23e7"),a=o("6f53").entries;n({target:"Object",stat:!0},{entries:function(t){return a(t)}})},6376:function(t,e,o){"use strict";o.r(e);var n=function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{staticClass:"app-container"},[o("el-button",{attrs:{type:"primary"},on:{click:t.toAddJob}},[t._v("新建任务")]),o("br"),o("br"),o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],attrs:{data:t.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[o("el-table-column",{attrs:{align:"center",label:"ID",width:"95"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.$index+1)+" ")]}}])}),o("el-table-column",{attrs:{label:"JOB_GROUP",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.JOB_GROUP)+" ")]}}])}),o("el-table-column",{attrs:{label:"JOB_NAME",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.JOB_NAME)+" ")]}}])}),o("el-table-column",{attrs:{label:"TRIGGER_GROUP",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.TRIGGER_GROUP)+" ")]}}])}),o("el-table-column",{attrs:{label:"TRIGGER_NAME",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.TRIGGER_NAME)+" ")]}}])}),o("el-table-column",{attrs:{label:"CRON_EXPRESSION",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.CRON_EXPRESSION)+" ")]}}])}),o("el-table-column",{attrs:{label:"NEXT_FIRE_TIME",width:""},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.NEXT_FIRE_TIME)+" ")]}}])}),o("el-table-column",{attrs:{label:"TRIGGER_STATE",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.TRIGGER_STATE)+" ")]}}])}),o("el-table-column",{attrs:{label:"操作",width:"380"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-button",{attrs:{plain:"",type:"primary",size:"mini"},on:{click:function(o){return t.doRescheduleJob(e.row)}}},[t._v("修改")]),o("el-button",{attrs:{plain:"",type:"info",size:"mini"},on:{click:function(o){return t.showJobData(e.row)}}},[t._v("数据")]),t.paused(e.row)?o("el-button",{attrs:{plain:"",type:"warning",size:"mini"},on:{click:function(o){return t.doPause(e.row)}}},[t._v("暂停")]):o("el-button",{attrs:{plain:"",type:"primary",size:"mini"},on:{click:function(o){return t.doResume(e.row)}}},[t._v("恢复")]),o("el-button",{attrs:{plain:"",type:"success",size:"mini"},on:{click:function(o){return t.doRunJob(e.row)}}},[t._v("立即执行")]),o("el-button",{attrs:{plain:"",type:"danger",size:"mini"},on:{click:function(o){return t.doDeleteJob(e.row)}}},[t._v("删除")])]}}])})],1),o("div",{staticStyle:{"text-align":"center"}},[o("br"),o("el-pagination",{attrs:{background:"",layout:"prev, pager, next, sizes",total:t.total,"page-sizes":[10,20,30,50],"page-size":t.pageInfo.pageSize},on:{"current-change":t.handlePageChange,"size-change":t.handleSizeChange}})],1),o("el-dialog",{attrs:{title:"JobData",visible:t.dialogJobDataVisible,width:"50%"},on:{"update:visible":function(e){t.dialogJobDataVisible=e}}},[o("el-table",{attrs:{data:t.jobDataVo,"row-class-name":t.tableRowClassName,border:"",fit:"","highlight-current-row":""},on:{"row-click":t.handleRowClick}},[o("el-table-column",{attrs:{align:"center",label:"ID",width:"95"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.$index+1)+" ")]}}])}),o("el-table-column",{attrs:{label:"DATA_NAME",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row[0])+" ")]}}])}),o("el-table-column",{attrs:{label:"DATA_VALUE",width:""},scopedSlots:t._u([{key:"default",fn:function(e){return[e.$index==t.editRow?o("div",[o("el-input",{ref:"editInput",on:{blur:function(o){return t.jobDataChange(e.row,t.jobData[e.row[0]])}},nativeOn:{keyup:function(o){return!o.type.indexOf("key")&&t._k(o.keyCode,"enter",13,o.key,"Enter")?null:t.jobDataChange(e.row,t.jobData[e.row[0]])}},model:{value:t.jobData[e.row[0]],callback:function(o){t.$set(t.jobData,e.row[0],o)},expression:"jobData[scope.row[0]]"}})],1):o("div",[t._v(t._s(e.row[1]))])]}}])})],1)],1),o("el-dialog",{attrs:{title:"新建任务",visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[o("el-form",{attrs:{model:t.jobForm,"label-width":"80px"}},[o("el-form-item",{attrs:{label:"任务名"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:t.jobForm.jobName,callback:function(e){t.$set(t.jobForm,"jobName",e)},expression:"jobForm.jobName"}})],1),o("el-form-item",{attrs:{label:"发送人"}},[o("el-select",{staticStyle:{width:"100%"},attrs:{placeholder:"请选择发送人"},on:{change:t.senderChange},model:{value:t.jobForm.sender,callback:function(e){t.$set(t.jobForm,"sender",e)},expression:"jobForm.sender"}},t._l(t.botList,(function(t){return o("el-option",{key:t.id,attrs:{label:t.id+"("+t.nickname+")",value:t.id}})})),1)],1),o("el-form-item",{attrs:{label:"接收人"}},[o("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择接收人"},model:{value:t.jobForm.to,callback:function(e){t.$set(t.jobForm,"to",e)},expression:"jobForm.to"}},t._l(t.friendList,(function(t){return o("el-option",{key:t.id,attrs:{label:t.id+"("+t.nickname+"["+t.remark+"])",value:t.id}})})),1)],1),o("el-form-item",{attrs:{label:"消息内容"}},[o("el-input",{attrs:{rows:4,type:"textarea",autocomplete:"off"},model:{value:t.jobForm.msg,callback:function(e){t.$set(t.jobForm,"msg",e)},expression:"jobForm.msg"}})],1),o("el-form-item",{attrs:{label:"Cron"}},[o("el-input",{attrs:{autocomplete:"off"},model:{value:t.jobForm.cron,callback:function(e){t.$set(t.jobForm,"cron",e)},expression:"jobForm.cron"}})],1)],1),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),o("el-button",{attrs:{type:"primary"},on:{click:t.doAddJob}},[t._v("确 定")])],1)],1)],1)},a=[],i=(o("4fad"),o("b775"));function r(t){return Object(i["a"])({url:"/quartz/list",method:"get",params:t})}function s(t){return Object(i["a"])({url:"/quartz/jobData",method:"get",params:t})}function l(t){return Object(i["a"])({url:"/quartz/deleteJob",method:"get",params:t})}function u(t){return Object(i["a"])({url:"/quartz/runJob",method:"get",params:t})}function c(t){return Object(i["a"])({url:"/quartz/updateJobData",method:"post",data:t})}function d(t){return Object(i["a"])({url:"/quartz/rescheduleJob",method:"post",params:t})}function f(t){return Object(i["a"])({url:"/quartz/addJob",method:"post",data:t})}function b(t){return Object(i["a"])({url:"/quartz/pauseJob",method:"post",params:t})}function m(t){return Object(i["a"])({url:"/quartz/resumeJob",method:"post",params:t})}function h(){return Object(i["a"])({url:"/bot/list",method:"get"})}function p(t){return Object(i["a"])({url:"/bot/listFriends",method:"get",params:t})}var g={filters:{statusFilter:function(t){var e={published:"success",draft:"gray",deleted:"danger"};return e[t]}},data:function(){return{pageInfo:{pageNo:1,pageSize:10},total:0,editRowData:{},editRow:-1,jobDataVo:[],jobData:{},dialogJobDataVisible:!1,dialogFormVisible:!1,jobForm:{},list:null,botList:[],friendList:[],listLoading:!0}},created:function(){this.fetchData()},methods:{doPause:function(t){var e=this;b({jobGroup:t.JOB_GROUP,jobName:t.JOB_NAME}).then((function(t){e.$message.success(t.msg)})).catch((function(t){e.$message.error(t.msg)})).then((function(){e.fetchData()}))},doResume:function(t){var e=this;m({jobGroup:t.JOB_GROUP,jobName:t.JOB_NAME}).then((function(t){e.$message.success(t.msg)})).catch((function(t){e.$message.error(t.msg)})).then((function(){e.fetchData()}))},paused:function(t){return-1===t.TRIGGER_STATE.indexOf("PAUSED")},senderChange:function(t){var e=this;p({botId:t}).then((function(t){e.friendList=t.data})).catch((function(t){e.$message.error(t.msg)}))},doListBot:function(){var t=this;h().then((function(e){t.botList=e.data})).catch((function(e){t.$message.error(e.msg)}))},doAddJob:function(){var t=this;this.dialogFormVisible=!1,f(this.jobForm).then((function(e){t.$message.success(e.msg),t.fetchData()})).catch((function(e){t.$message.error(e.msg)}))},toAddJob:function(){this.doListBot(),this.dialogFormVisible=!0},handlePageChange:function(t){this.pageInfo.pageNo=t,this.fetchData()},handleSizeChange:function(t){this.pageInfo.pageSize=t,this.pageInfo.pageNo=1,this.fetchData()},fetchData:function(){var t=this;this.listLoading=!0,r(this.pageInfo).then((function(e){t.total=e.data.totalCount,t.list=e.data.pageData,t.listLoading=!1}))},showJobData:function(t){var e=this;s({jobGroup:t.JOB_GROUP,jobName:t.JOB_NAME}).then((function(o){e.jobData=o.data,e.jobDataVo=Object.entries(e.jobData),e.editRowData=t,e.dialogJobDataVisible=!0}))},doDeleteJob:function(t){var e=this;this.$confirm("是否删除此任务?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){l({jobGroup:t.JOB_GROUP,jobName:t.JOB_NAME}).then((function(t){e.$message.success(t.msg),e.fetchData()})).catch((function(t){e.$message.error(t.msg)}))}))},doRunJob:function(t){var e=this;this.$confirm("是否立即执行此任务?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){u({jobGroup:t.JOB_GROUP,jobName:t.JOB_NAME}).then((function(t){e.$message.success(t.msg)})).catch((function(t){e.$message.error(t.msg)}))}))},tableRowClassName:function(t){var e=t.row,o=t.rowIndex;e.index=o},handleRowClick:function(t,e,o){var n=this;this.editRow=t.index,this.$nextTick((function(){n.$refs.editInput.focus()}))},jobDataChange:function(t,e){var o=this;this.editRow=-1,t[1]!==e&&(t[1]=e,console.log(t),c({jobGroup:this.editRowData.JOB_GROUP,jobName:this.editRowData.JOB_NAME,jobData:this.jobData}).then((function(t){})).catch((function(t){o.$message.error(t.msg)})))},doRescheduleJob:function(t){var e=this;this.$prompt("请输入Cron表达式","提示",{confirmButtonText:"确定",cancelButtonText:"取消"}).then((function(o){var n=o.value;d({triggerGroup:t.TRIGGER_GROUP,triggerName:t.TRIGGER_NAME,cron:n}).then((function(t){e.$message.success(t.msg),e.fetchData()})).catch((function(t){e.$message.error(t.msg)}))}))}}},_=g,w=o("2877"),v=Object(w["a"])(_,n,a,!1,null,null,null);e["default"]=v.exports},"6f53":function(t,e,o){var n=o("83ab"),a=o("df75"),i=o("fc6a"),r=o("d1e7").f,s=function(t){return function(e){var o,s=i(e),l=a(s),u=l.length,c=0,d=[];while(u>c)o=l[c++],n&&!r.call(s,o)||d.push(t?[o,s[o]]:s[o]);return d}};t.exports={entries:s(!0),values:s(!1)}}}]);