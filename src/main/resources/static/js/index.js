$(function () {
    /**
     * 获取表列表
     */
    var map = {};
    var $formTemplate = $("#formTemplate").children();
    var $list = $("#tabs_list");
    var $tabContent = $list.find(".tab-content");
    var $ui = $list.find("ul");
    var $selectTableList = $("#selectTable");
    var $step1 = $("#step1");
    var $step2 = $("#step2");
    var $step3 = $("#step3");
    var $step4 = $("#step4");
    var $step5 = $("#step5");
    var $step6 = $("#step6");
    var $businessModule = $("#businessModule");
    var selectTable = [];
    var modalDialog;
    var modalDialogForm;
    var allFields;
    var modalDialogTips = $(".validateTips");
    var modalList = [];
    var $configSelectTable = $("#configSelectTable");
    $step1.show();
    $step2.hide();
    $step3.hide();
    $step4.hide();
    $step5.hide();
    $step6.hide();
    function genTable(value, index) {
        var $formTmp = $formTemplate.clone();
        if (index == 0) {
            $formTmp.addClass("active");
        }
        var $form = $formTmp.find("form");
        var $tbody = $form.find(".colmunEdit tbody");
        var $tr = $tbody.find("tr");
        $tbody.empty();
        $.each($form.find(".table-edit :input"), function (index, obj) {
            var $this = $(this);
            var name = $this.attr("name");
            if (value[name]) {
                $this.val(value[name])
            }
        });
        var columns = value.columnList || [];
        columns.forEach(function (columnData, index) {
            var $trTmp = $tr.clone();
            $.each($trTmp.find(":input"), function (index, obj) {
                var $this = $(this);
                var name = $this.attr("name");
                if (columnData[name]) {
                    $this.val(columnData[name])
                }
            });
            if (columnData["allowNull"]) {
                $trTmp.find(":checkbox[name = 'allowNull']").attr("checked", true);
            } else {
                $trTmp.find(":checkbox[name = 'allowNull']").attr("checked", false);
            }
            $tbody.append($trTmp);
        });
        $formTmp.attr("id", value.tableName);
        $tabContent.append($formTmp);
    }

    function updateTips(t) {
        modalDialogTips
            .text(t)
            .addClass("ui-state-highlight");
        setTimeout(function () {
            modalDialogTips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    function checkLength(o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips("Length of " + n + " must be between " +
                min + " and " + max + ".");
            return false;
        } else {
            return true;
        }
    }

    function checkRegexp(o, regexp, n) {
        if (!( regexp.test(o.val()) )) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        } else {
            return true;
        }
    }

    function addBusinessModule() {
        var businessModule = $("#businessModuleInput");
        var businessModuleName = $("#businessModuleNameInput");
        var businessModuleDesc = $("#businessModuleDescInput");
        var moduleUnionTableName = $("#moduleUnionTableName");
        allFields = $([]).add(businessModule).add(businessModuleName).add(businessModuleDesc).add(moduleUnionTableName);
        var valid = true;
        allFields.removeClass("ui-state-error");
        valid = valid && checkLength(businessModule, "分类标识", 1, 16);
        valid = valid && checkLength(businessModuleName, "分类菜单名", 1, 50);
        valid = valid && checkRegexp(businessModule, /^[a-z]([0-9a-z_\s])+$/i, "分类标识只能以字母开头，允许输入字母和数字");
        if (valid) {
            var businessModuleVal = businessModule.val();
            var businessModuleNameVal = businessModuleName.val();
            var businessModuleDescVal = businessModuleDesc.val() || '';
            var moduleUnionTableNameList = [];
            $.each(moduleUnionTableName.find(":selected"), function () {
                var $this = $(this);
                moduleUnionTableNameList.push($this.val());
            });
            var moduleUnionTableNameVal = moduleUnionTableNameList.join(",");
            $businessModule.find("tbody").append("<tr>" +
                "<td>" + businessModuleVal + "</td>" +
                "<td>" + businessModuleNameVal + "</td>" +
                "<td>" + businessModuleDescVal + "</td>" +
                "<td>" + moduleUnionTableNameVal + "</td>" +
                "</tr>");
            modalList.push({
                businessModule: businessModuleVal,
                businessModuleName: businessModuleNameVal,
                businessModuleDesc: businessModuleDescVal,
                moduleUnionTableName: moduleUnionTableNameVal
            })
            var moduleUnionTableNameMap = {};
            moduleUnionTableNameList.forEach(function (obj, index) {
                moduleUnionTableNameMap[obj] = true;
            });
            var selectOption = [];
            selectTable.forEach(function (obj, index) {
                if (!moduleUnionTableNameMap[obj.id]) {
                    selectOption.push(obj);
                }
            });
            moduleUnionTableName.empty();
            moduleUnionTableName.select2(
                {data: selectOption, width: 'resolve'}
            );
            moduleUnionTableName.val(null).trigger("change");
            selectTable = selectOption;
            modalDialog.dialog("close");
        }
        return valid;
    }

    modalDialog = $("#modalDialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 500,
        modal: true,
        buttons: {
            "新增分类": addBusinessModule,
            "取消": function () {
                modalDialog.dialog("close");
            }
        },
        close: function () {
            modalDialogForm[0].reset();
            // allFields.removeClass("ui-state-error");
        }
    });
    modalDialogForm = modalDialog.find("form");
    $("#addBusinessModuleBtn").click(function () {
        modalDialog.dialog("open");
    });
    $("#step1NextBtn").click(function () {
        $step1.hide();
        $step2.show();
    });
    $("#step2NextBtn").click(function () {
        var url = $("#url").val();
        var userName = $("#username").val();
        var password = $("#password").val();
        var dbName = $("#dbName").val();
        var packageName = $("#packageName").val();
        var projectName = $("#projectName").val();
        var projectDesc = $("#projectDesc").val();

        $.ajax({
            type: 'POST',
            url: base_url + "/tableList",
            data: {
                "url": url,
                "userName": userName,
                "password": password,
                "dbName": dbName,
                "packageName": packageName,
                "projectName": projectName,
                "projectDesc": projectDesc
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var dataList = data.data || [];
                    map = {};
                    $selectTableList.empty();
                    var package = packageName;
                    if (projectName) {
                        package = package + "." + projectName;
                    }
                    dataList.forEach(function (value, index) {
                        map[value.tableName] = value;
                        value.packagePath = package;
                        $selectTableList.append("<div class=\"row\" style='border-bottom: 1px solid #eef'>"
                            + "<div class=\"col-md-2\"><input type=\"checkbox\" checked name=\"tables\" value='" + value.tableName + "'/></div>"
                            + "<div class=\"col-md-5\">" + value.tableName + "</div>"
                            + "<div class=\"col-md-5\">" + value.desc + "</div>"
                            + "</div>");
                    });
                    $step2.hide();
                    $step3.show();
                } else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || '数据库链接失败')
                    });
                }
            },
            error: function (error) {
                layer.open({
                    icon: '2',
                    content: ('数据库链接失败，请检查配置！' + error.message || '')
                });
            }
        });
    });
    $("#step2PrevBtn").click(function () {
        $step2.hide();
        $step1.show();
    });
    $("#step3PrevBtn").click(function () {
        $step3.hide();
        $step2.show();
    });
    $("#tableSelectAll").change(function () {
        var $this = $(this);
        var checked = $this.prop("checked");
        $selectTableList.find(":checkbox").prop("checked", checked);
    })
    $("#step3NextBtn").click(function () {
        var $checked = $selectTableList.find(":checked");
        if ($checked.length < 1) {
            layer.open({
                icon: '2',
                content: ("至少要选择一张表")
            });
            return false;
        }
        console.error($checked.length);
        $tabContent.empty();
        $ui.find("a").parent().remove();
        var $businessModuleBody = $businessModule.find("body");
        var tableBodyValue = "";
        selectTable = [];
        $.each($checked, function (index) {
            var $this = $(this);
            var tableName = $this.val();
            var value = map[tableName]
            selectTable.push({id: tableName, text: value.desc + "(" + tableName + ")"});
            // var value = map[tableName];
            // $businessModuleBody.empty();
            // var $tr = "<tr>"
            // <td>分类标识</td>
            // <td>分类菜单名</td>
            // <td>描述</td>
            // <td>分类关联表</td>
            // <td>操作</td>
            // $tr+=("<td>"+value.businessModule+"</td>"+"<td>"+value.businessModuleName+"</td>"+"<td>"+value.businessModuleDesc+"</td>"+"<td>"+value.tables+"</td>"
            //      +"<td><button onClick='editBusinessModule(\""++"\")'>编辑</button></td>")
            // $tr += "</tr>";
            // tableBodyValue += $tr;
        });
        $('#moduleUnionTableName').select2(
            {data: selectTable, width: 'resolve'}
        );
        $step3.hide();
        $step4.show();
    });
    $("#step4PrevBtn").click(function () {
        $step4.hide();
        $step3.show();
    });
    function replace() {
        /*
         *   trs += '<td><input type="text" name="modulName" value=""></td>'
         trs += '<td><input type="text" name="modul" value=""></td>'
         trs += '<td>' + obj + '</td>'
         trs += '<td><input type="text" name="className" value="' + currObj.name + '"></td>'
         trs += '<td><input type="text" name="desc" value="' + currObj.desc  + '"></td>'
         trs += '<td><input type="checkbox" name="add" value="true" checked></td>'
         trs += '<td><input type="checkbox" name="edit" value="true" checked></td>'
         trs += '<td><input type="checkbox" name="detail" value="true" checked></td>'
         trs += '<td><input type="checkbox" name="delete" value="true" checked></td>'
         trs += '<td><input type="checkbox" name="import" value="true" checked></td>'
         trs += '<td><input type="checkbox" name="export" value="true" checked></td>'
         * */
        $.each($configSelectTable.find("tbody tr"), function () {
            var $this = $(this);
            var $td = $this.find("td");
            var businessModuleName = $td.get(0).firstChild.value;
            var businessModule = $td.get(1).firstChild.value;
            var tableName = $td.get(2).innerText;
            var className = $td.get(3).firstChild.value;
            var desc = $td.get(4).firstChild.value;
            var isAdd = $td.get(5).firstChild.checked;
            var isEdit = $td.get(6).firstChild.checked;
            var isDetail = $td.get(7).firstChild.checked;
            var isDelete = $td.get(8).firstChild.checked;
            var isImport = $td.get(9).firstChild.checked;
            var isExport = $td.get(10).firstChild.checked;
            var obj = map[$.trim(tableName)];
            obj.businessModule = $.trim(businessModule);
            obj.businessModuleDesc = $.trim(businessModuleName);
            obj.desc = $.trim(desc);
            obj.name = $.trim(className);
            obj.isAdd = isAdd;
            obj.isEdit = isEdit;
            obj.isDetail = isDetail;
            obj.isDelete = isDelete;
            obj.isImport = isImport;
            obj.isExport = isExport;
        });
    }

    $("#step4NextBtn").click(function () {
        var $checked = $selectTableList.find(":checked");
        if ($checked.length < 1) {
            layer.open({
                icon: '2',
                content: ("至少要选择一张表")
            });
            return false;
        }
        $step4.hide();
        var $tbody = $configSelectTable.find("tbody");
        $tbody.empty();
        var tables = selectTable.map(function (o) {
            return o.id
        });
        var trs = "";
        modalList.forEach(function (obj, index) {
            if (obj.moduleUnionTableName) {
                var list = obj.moduleUnionTableName.split(",");
                list.forEach(function (tableName, index) {
                    var currObj = map[tableName];
                    var pointer = tables.indexOf(tableName);
                    tables.splice(pointer, 0)
                    trs += '<tr>';
                    trs += '<td><input type="text" name="modulName" value="' + obj.businessModuleName + '"></td>'
                    trs += '<td><input type="text" name="modul" value="' + obj.businessModule + '"></td>'
                    trs += '<td>' + tableName + '</td>'
                    trs += '<td><input type="text" name="className" value="' + currObj.name + '">' + currObj.name + '</td>'
                    trs += '<td><input type="text" name="desc" value="' + currObj.desc + '"></td>'
                    trs += '<td><input type="checkbox" name="add" value="true" checked></td>'
                    trs += '<td><input type="checkbox" name="edit" value="true" checked></td>'
                    trs += '<td><input type="checkbox" name="detail" value="true" checked></td>'
                    trs += '<td><input type="checkbox" name="delete" value="true" checked></td>'
                    trs += '<td><input type="checkbox" name="import" value="true" checked></td>'
                    trs += '<td><input type="checkbox" name="export" value="true" checked></td>'
                    trs += '</tr>';
                })
            }
        });
        tables.forEach(function (obj, index) {
            var currObj = map[obj];
            trs += '<tr>';
            trs += '<td><input type="text" name="modulName" value=""></td>'
            trs += '<td><input type="text" name="modul" value=""></td>'
            trs += '<td>' + obj + '</td>'
            trs += '<td><input type="text" name="className" value="' + currObj.name + '"></td>'
            trs += '<td><input type="text" name="desc" value="' + currObj.desc + '"></td>'
            trs += '<td><input type="checkbox" name="add" value="true" checked></td>'
            trs += '<td><input type="checkbox" name="edit" value="true" checked></td>'
            trs += '<td><input type="checkbox" name="detail" value="true" checked></td>'
            trs += '<td><input type="checkbox" name="delete" value="true" checked></td>'
            trs += '<td><input type="checkbox" name="import" value="true" checked></td>'
            trs += '<td><input type="checkbox" name="export" value="true" checked></td>'
            trs += '</tr>';
        })
        $tbody.append(trs);
        $step5.show();
    });

    $("#step5PrevBtn").click(function () {
        $step5.hide();
        $step4.show();
    });

    $("#step5NextBtn").click(function () {
        var $checked = $selectTableList.find(":checked");
        if ($checked.length < 1) {
            layer.open({
                icon: '2',
                content: ("至少要选择一张表")
            });
            return false;
        }
        $step5.hide();
        replace();
        $tabContent.empty();
        $ui.find("a").parent().remove();

        $.each($checked, function (index) {
            var $this = $(this);
            var tableName = $this.val();
            var value = map[tableName];
            var active = "";
            if (index == 0) {
                active = "active";
            }
            var $li = $("<li style='width: 200px' class=\"" + active + "\" ><a href=\"#" + value.tableName + "\" data-toggle=\"tab\">" + value.tableName + "</a></li>");
            $ui.append($li);
            genTable(value, index)
        });

        var width = $ui.find("li").length * 200;
        $ui.parent("div").css("width", width+"px");
        $step6.show();
    });
    $("#step6PrevBtn").click(function () {
        $step6.hide();
        $step5.show();
    })
    $("#genCodeBtn").click(function () {
        var param = [];
        $.each($("form"), function () {
            var $this = $(this);
            var $tableEdit = $this.find(".table-edit");
            var tableName = $this.find(":text[name='tableName']").val();
            if (!tableName) {
                return;
            }
            var dataObj = map[tableName];
            $.each($tableEdit.find("input"), function () {
                var $element = $(this);
                var name = $element.attr("name");
                dataObj[name] = $.trim($element.val());
            });
            var columnObjs = dataObj.columnList || [];
            var clonumMap = {};
            columnObjs.forEach(function (obj) {
                clonumMap[obj.columnName] = obj;
            });
            var $colnumList = $this.find(".colmunEdit tbody tr");
            $.each($colnumList, function () {
                var $colnumTr = $(this);
                var columnName = $colnumTr.find(":text[name='columnName']").val();
                var colnumObj = clonumMap[columnName];
                $.each($colnumTr.find(":input"), function () {
                    var $input = $(this);
                    if ($input.attr("type") == 'checkbox') {
                        var name = $input.attr("name");
                        var value = $input.val();
                        colnumObj[name] = $input.prop("checked");
                    } else {
                        var name = $input.attr("name");
                        var value = $input.val();
                        colnumObj[name] = value;
                    }
                });

            })
        })
        var $checked = $selectTableList.find(":checked");
        if ($checked.length < 1) {
            layer.open({
                icon: '2',
                content: ("至少要选择一张表")
            });
            return false;
        }
        $tabContent.empty();
        $ui.find("a").parent().remove();
        $.each($checked, function (index) {
            var $this = $(this);
            var tableName = $this.val();
            param.push(map[tableName]);
        });
        var jdbcUrl = $("#url").val();
        var jdbcUserName = $("#username").val();
        var jdbcPassword = $("#password").val();
        var packageName = $("#packageName").val();
        var projectName = $("#projectName").val();
        var projectDesc = $("#projectDesc").val();
        //创建表单对象
        var _form = $("<form></form>", {
            'id': 'tempForm',
            'method': 'post',
            'action': "/codeGenerate",
            'target': "_blank",
            'style': 'display:none'
        }).appendTo($("body"));
        _form.append($("<input>", {'type': 'hidden', 'name': "data", 'value': JSON.stringify(param)}));
        _form.append($("<input>", {'type': 'hidden', 'name': "jdbcUrl", 'value': jdbcUrl}));
        _form.append($("<input>", {'type': 'hidden', 'name': "jdbcUsername", 'value': jdbcUserName}));
        _form.append($("<input>", {'type': 'hidden', 'name': "jdbcPassword", 'value': jdbcPassword}));
        _form.append($("<input>", {'type': 'hidden', 'name': "packageName", 'value': packageName}));
        _form.append($("<input>", {'type': 'hidden', 'name': "projectName", 'value': projectName}));
        _form.append($("<input>", {'type': 'hidden', 'name': "projectDesc", 'value': projectDesc}));
        _form.trigger("submit");
        _form.remove();
    });
})