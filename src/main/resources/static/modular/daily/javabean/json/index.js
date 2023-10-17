$("#javabean").change(function() {
	var javabean = $("#javabean").val()
	var jsonBeanArr = javabean.split("\n");

	var beanType = ["String", "Integer", "Long", "LocalDate", "LocalDateTime"]

	var map = {
		"type": "object"
	}
	var json = {};
	map["properties"] = json
	for (let i = 0; i < jsonBeanArr.length; i++) {
		var row = jsonBeanArr[i];
		if(!row) {
			continue;
		}

		for (let type of beanType) {
			if(row.indexOf(" "+type+" ")>-1) {
				var rows = row.split(" ");
				var key = rows[rows.length-1].replaceAll(";", "")
				json[key] = {}

				json[key]["type"] = type=="Integer" || type=="Long"? "number":
					type=="LocalDate" || type=="LocalDateTime"? "string": "string"

				var row_1 = jsonBeanArr[i-1]
				if(row_1) {
					if (row_1.indexOf("//")>-1) {
						row_1 = row_1.replaceAll("//").trim();
						json[key]["description"]=row_1;
					}
					if(row_1.indexOf("@ApiModelProperty")>-1) {
						row_1 = row_1.replaceAll(/@ApiModelProperty\((value ?=? ?)/g, "").replaceAll(/\"/g, "").replaceAll(/\)/g, "").trim();
						json[key]["description"]=row_1;
					}
				}

				var row_2 = jsonBeanArr[i-2]
				if(row_1) {
					if (row_2.indexOf("//") > -1) {
						row_2 = row_2.replaceAll("//").trim();
						json[key]["description"] = row_2;
					}
					if (row_2.indexOf("@ApiModelProperty") > -1) {
						row_2 = row_2.replaceAll(/@ApiModelProperty\((value ?=? ?)/g, "").replaceAll(/\"/g, "").replaceAll(/\)/g, "").trim();
						json[key]["description"] = row_2;
					}
				}
			}
		}

	}

	$("#json").html(JSON.stringify(map))
})
