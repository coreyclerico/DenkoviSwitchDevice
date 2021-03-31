/**
 *
 *
 */

metadata {
	definition (name: "DenkoviValveDevice", namespace: "denkoviswitch", author: "Corey Cleric") {
		capability "Valve"
        capability "Refresh"
	}
    preferences {
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
}

void updated() {
    log.info "Updated..."
    log.warn "description logging is: ${txtEnable == true}"
}

void installed() {
    log.info "Installed..."
    device.updateSetting("txtEnable",[type:"bool",value:true])
    refresh()
}

void parse(String description) { log.warn "parse(String description) not implemented" }

void parse(List description) {
    description.each {
        if (it.name in ["valve"]) {
            if (txtEnable) log.info it.descriptionText
            sendEvent(it)
        }
    }
}
void refresh() {
	parent?.componentRefresh(this.device)
}

void open() {
    parent?.componentOn(this.device)
}

void close() {
    parent?.componentOff(this.device)
}  
