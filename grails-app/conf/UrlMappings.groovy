class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/" {
			controller = "table"
			action = "table"
		}
		"500"(view:'/error')
	}
}
