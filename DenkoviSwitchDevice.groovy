/**
 * Denkovi Switch Device 
 *
 */

def version() { return "denkovi-20210331" }

metadata {
	definition (name: "DenkoviSwitchDevice", namespace: "denkoviswitch", author: "Corey Cleric") {
		capability "Switch"
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
        if (it.name in ["switch"]) {
            if (txtEnable) log.info it.descriptionText
            sendEvent(it)
        }
    }
}
void refresh() {
	parent?.componentRefresh(this.device)
}

void on () {
    parent?.componentOn(this.device)
}

void off () {
    parent?.componentOff(this.device)
}  
