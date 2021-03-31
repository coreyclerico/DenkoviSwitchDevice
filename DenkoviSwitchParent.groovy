/*
DenkoviSwitch Parent
24VAC Power \ DenkoviSwitch Web Gateway \ PoE \ Power loss results to all off \ closed
Inital release 0.1.0 3-31-2021
*/


metadata {
    definition (name: "DenkoviSwitchParent", namespace: "denkoviswitch", author: "Corey Cleric") {
        capability "Configuration"
        
        command "switchAllOff"
        command "childSwitch1On"
        command "childSwitch1Off"
        command "childSwitch2On"
        command "childSwitch2Off"
        command "childSwitch3On"
        command "childSwitch3Off"
        command "childSwitch4On"
        command "childSwitch4Off"
        command "childSwitch5On"
        command "childSwitch5Off"
        command "childSwitch6On"
        command "childSwitch6Off"
        command "childSwitch7On"
        command "childSwitch7Off"
        command "childSwitch8On"
        command "childSwitch8Off"
        command "refresh"
              
    }
    preferences {
        input name: "denkovi_model", type: "text", title: "Denkovi Model", defaultValue: "smartDen IP-Maxi Module", required: false
        input name: "denkovi_password", type: "text", title: "Denkovi Password", defaultValue: "Pool4Fun22", required: false
        input name: "internal_ip", type: "text", title: "Internal IP", defaultValue: "172.16.1.192", required: false
        input name: "internal_port", type: "text", title: "Internal Port", defaultValue: "80", required: false
        input name: "switch1name", type: "text", title: "Switch 1 Name", defaultValue: "Denkovi Relay One", required: true
        input name: "switch2name", type: "text", title: "Switch 2 Name", defaultValue: "Denkovi Relay Two", required: true
        input name: "switch3name", type: "text", title: "Switch 3 Name", defaultValue: "Denkovi Relay Three", required: true
        input name: "switch4name", type: "text", title: "Switch 4 Name", defaultValue: "Denkovi Relay Four", required: true
        input name: "switch5name", type: "text", title: "Switch 5 Name", defaultValue: "Denkovi Relay Five", required: true
        input name: "switch6name", type: "text", title: "Switch 6 Name", defaultValue: "Denkovi Relay Six", required: true
        input name: "switch7name", type: "text", title: "Switch 7 Name", defaultValue: "Denkovi Relay Seven", required: true
        input name: "switch8name", type: "text", title: "Switch 8 Name", defaultValue: "Denkovi Relay Eight", required: true
        input name: "switch1type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch2type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch3type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch4type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch5type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch6type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch7type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "switch8type", type: "text", title: "valve", defaultValue: "switch", required: true
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: false

    }
}

void parse(String description) {
    // your parser.
}

def refresh() {
    log.info "Denkovi ${denkovi_model}:${internal_ip} updated..."
	initialize()
    }

def logsOff(){
    log.info "Denkovi ${denkovi_model}:${internal_ip} debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def updated(){
    log.info "Denkovi ${denkovi_model}:${internal_ip} Updated..."
    log.warn "Denkovi ${denkovi_model}:${internal_ip} debug logging is: ${logEnable == true}"
    log.warn "Denkovi ${denkovi_model}:${internal_ip} description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)
    unsubscribe()
    unschedule()
    initialize()
}

def childSwitch1On() { state.Relay1 = "on"; childSwitchOn(switch1name,1,switch1type) }
def childSwitch2On() { state.Relay2 = "on"; childSwitchOn(switch2name,2,switch2type) }
def childSwitch3On() { state.Relay3 = "on"; childSwitchOn(switch3name,3,switch3type) }
def childSwitch4On() { state.Relay4 = "on"; childSwitchOn(switch4name,4,switch4type) }
def childSwitch5On() { state.Relay5 = "on"; childSwitchOn(switch5name,5,switch5type) }
def childSwitch6On() { state.Relay6 = "on"; childSwitchOn(switch6name,6,switch6type) }
def childSwitch7On() { state.Relay7 = "on"; childSwitchOn(switch7name,7,switch7type) }
def childSwitch8On() { state.Relay8 = "on"; childSwitchOn(switch8name,8,switch8type) }
    
def childSwitch1Off() { state.Relay1 = "off"; childSwitchOff(switch1name,1,switch1type) }
def childSwitch2Off() { state.Relay2 = "off"; childSwitchOff(switch2name,2,switch2type) }
def childSwitch3Off() { state.Relay3 = "off"; childSwitchOff(switch3name,3,switch3type) }
def childSwitch4Off() { state.Relay4 = "off"; childSwitchOff(switch4name,4,switch4type) }
def childSwitch5Off() { state.Relay5 = "off"; childSwitchOff(switch5name,5,switch5type) }
def childSwitch6Off() { state.Relay6 = "off"; childSwitchOff(switch6name,6,switch6type) }
def childSwitch7Off() { state.Relay7 = "off"; childSwitchOff(switch7name,7,switch7type) }
def childSwitch8Off() { state.Relay8 = "off"; childSwitchOff(switch8name,8,switch8type) }


def switchAllOff() {
    try {
        log.info "Denkovi ${denkovi_model}:${internal_ip} Turning off all controlSwitch Devices" 
    	def result = new hubitat.device.HubAction(method: "GET", path: "/current_state.xml?pw=${denkovi_password}&Relay1=0&Relay2=0&Relay3=0&Relay4=0&Relay5=0&Relay6=0&Relay7=0&Relay8=0", headers: [HOST: "${internal_ip}:${internal_port}"])
        //log.debug "Denkovi ${denkovi_model}:${internal_ip} Result={$result}" 
    	sendHubCommand(result)
        }
    catch (e) {	log.info "Denkovi ${denkovi_model}:${internal_ip} Hit Exception ${e} ${result}"	}
}


def childSwitchOn(sname,sindex,stype){
    String myChildDevice = "${device.id}-${sname.replaceAll("\\s","")}"
    log.info "Denkovi ${denkovi_model}:${internal_ip} Turn Device ${myChildDevice} on"
     try {
         def result = new hubitat.device.HubAction(method: "GET", path: "/current_state.xml?pw=${denkovi_password}&Relay${sindex}=1", headers: [HOST: "${internal_ip}:${internal_port}"])
        if (logEnable) log.debug "Denkovi ${denkovi_model}:${internal_ip} Result={$result}" 
    	sendHubCommand(result)
        }
    catch (e) {	log.debug "Denkovi ${denkovi_model}:${internal_ip} Hit Exception ${e} ${result}"	}
    def child = getChildDevice(myChildDevice)
    switch (stype) {
        case "switch":
            child.sendEvent(name: "switch", value: "on")
            break
        case "valve":
            child.sendEvent(name: "valve", value: "open")
            break
        }
}

def childSwitchOff(sname,sindex,stype){
    String myChildDevice = "${device.id}-${sname.replaceAll("\\s","")}"
    log.info "Denkovi ${denkovi_model}:${internal_ip} Turn Device ${myChildDevice} off"
     try {
        def result = new hubitat.device.HubAction(method: "GET", path: "/current_state.xml?pw=${denkovi_password}&Relay${sindex}=0", headers: [HOST: "${internal_ip}:${internal_port}"])
        if (logEnable) log.debug "Denkovi ${denkovi_model}:${internal_ip} Result={$result}" 
    	sendHubCommand(result)
        }
    catch (e) {	log.debug "Denkovi ${denkovi_model}:${internal_ip} Hit Exception ${e} ${result}"	}
    def child = getChildDevice(myChildDevice)
    switch (stype) {
        case "switch":
            child.sendEvent(name: "switch", value: "off")
            break
        case "valve":
            child.sendEvent(name: "valve", value: "closed")
            break
        }

}


def setupControlSwitchChildren() {
    setupControlSwitchChild(switch1name,switch1type)
    setupControlSwitchChild(switch2name,switch2type)
    setupControlSwitchChild(switch3name,switch3type)
    setupControlSwitchChild(switch4name,switch4type)
    setupControlSwitchChild(switch5name,switch5type)
    setupControlSwitchChild(switch6name,switch6type)
    setupControlSwitchChild(switch7name,switch7type)
    setupControlSwitchChild(switch8name,switch8type)
    }

def setupControlSwitchChild(sname,stype) {
    String myChildDevice = "${device.id}-${sname.replaceAll("\\s","")}"
    if (logEnable) log.debug "Denkovi ${denkovi_model}:${internal_ip} Device = ${myChildDevice}"
    def child = getChildDevice(myChildDevice)
    if (!child) { 
        switch (stype) {
            case "switch":
                addChildDevice("denkoviswitch", "DenkoviSwitchDevice", "${myChildDevice}", [name: "${sname}", isComponent: true])
                child = getChildDevice(myChildDevice)
                child.sendEvent(name: "switch", value: "off")
                break
            case "valve":
                addChildDevice("denkoviswitch", "DenkoviValveDevice", "${myChildDevice}", [name: "${sname}", isComponent: true])
                child = getChildDevice(myChildDevice)
                child.sendEvent(name: "valve", value: "closed")
                break
            }
        }
}

def componentRefresh(child){
    if (logEnable) log.debug "Denkovi ${denkovi_model}:${internal_ip} received refresh request from ${child.displayName}"
}

def componentOn(child){
    getChildDevice(child.deviceNetworkId).parse([[name:"switch", value:"on", descriptionText:"${child.displayName} was turned on"]])
    if (child.displayName == switch1name) { state.Relay1 = "on"; childSwitchOn(switch1name,1,switch1type) }
    if (child.displayName == switch2name) { state.Relay2 = "on"; childSwitchOn(switch2name,2,switch2type) }
    if (child.displayName == switch3name) { state.Relay3 = "on"; childSwitchOn(switch3name,3,switch3type) }
    if (child.displayName == switch4name) { state.Relay4 = "on"; childSwitchOn(switch4name,4,switch4type) }
    if (child.displayName == switch5name) { state.Relay5 = "on"; childSwitchOn(switch5name,5,switch5type) }
    if (child.displayName == switch6name) { state.Relay6 = "on"; childSwitchOn(switch6name,6,switch6type) }
    if (child.displayName == switch7name) { state.Relay7 = "on"; childSwitchOn(switch7name,7,switch7type) }
    if (child.displayName == switch8name) { state.Relay8 = "on"; childSwitchOn(switch8name,8,switch8type) }
  }

def componentOff(child){
    getChildDevice(child.deviceNetworkId).parse([[name:"switch", value:"off", descriptionText:"${child.displayName} was turned off"]])
    if (child.displayName == switch1name) { state.Relay1 = "off"; childSwitchOff(switch1name,1,switch1type) }
    if (child.displayName == switch2name) { state.Relay2 = "off"; childSwitchOff(switch2name,2,switch2type) }
    if (child.displayName == switch3name) { state.Relay3 = "off"; childSwitchOff(switch3name,3,switch3type) }
    if (child.displayName == switch4name) { state.Relay4 = "off"; childSwitchOff(switch4name,4,switch4type) }
    if (child.displayName == switch5name) { state.Relay5 = "off"; childSwitchOff(switch5name,5,switch5type) }
    if (child.displayName == switch6name) { state.Relay6 = "off"; childSwitchOff(switch6name,6,switch6type) }
    if (child.displayName == switch7name) { state.Relay7 = "off"; childSwitchOff(switch7name,7,switch7type) }
    if (child.displayName == switch8name) { state.Relay8 = "off"; childSwitchOff(switch8name,8,switch8type) }
}

def pollDenkoviSwitch() {
    log.info "Denkovi ${denkovi_model}:${internal_ip} pollDenkoviSwitch..."
    try {
        def result = new hubitat.device.HubAction([method: "GET", path: "/current_state.xml?pw=${denkovi_password}",headers: [HOST: "${internal_ip}:${internal_port}",]],null,[callback: parseResponse])
        sendHubCommand(result)
        //log.debug "Denkovi ${denkovi_model}:${internal_ip} Result={$result}" 
        }
    catch (e) {	log.debug "Denkovi ${denkovi_model}:${internal_ip} Hit Exception ${e} ${result}" }

}
                                                                                                
def parseResponse(resp) {
    //log.debug "Updated with resp: ${resp.toString()}"
    //log.debug "Updated with resp.body: ${resp.body.toString()}"

    if (resp.body.toString().contains('304 Redirect')) { 
        log.debug "Denkovi ${denkovi_model}:${internal_ip} Cannot read web page - must be opened on a browser"
        return
    }
    
    def payload = new XmlSlurper().parseText(resp.body)
    int myuptime = getLocation().getHub().uptime
    
    // Parse Relays
    
    for (int myindex = 1; myindex <= 8; myindex++) {
        mynewname = payload."Relay${myindex}".Name.text()
        mynewvalue = syncOnOff((payload."Relay${myindex}".Value.text()))
        if (mynewname != state."RelayName${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewname ${mynewname} is not state.RelayName${myindex} ${state."RelayName${myindex}"}, syncing...."
            state."RelayName${myindex}" = mynewname
        }
        if (mynewvalue != state."Relay${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewvalue ${mynewvalue} is not state.Relay${myindex} ${state."Relay${myindex}"}, syncing...."
            state."Relay${myindex}" = mynewvalue 
           }         
    }       
    
    // Parse Digital Inputs
    
    for (int myindex = 1; myindex <= 8; myindex++) {
        mynewname = payload."DigitalInput${myindex}".Name.text()
        mynewinput = syncOnOff((payload."DigitalInput${myindex}".Value.text()))
        mynewcount = payload."DigitalInput${myindex}".Count.toInteger()
        // log.debug "Denkovi ${denkovi_model}:${internal_ip} Index ${myindex} ${state."newDigitalInput${myindex}"} ${state."newDigitalCount${myindex}"} "
        if (mynewname != state."DigitalName${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewname ${mynewname} is not state.DigitalName${myindex} ${state."DigitalName${myindex}"}, syncing...."
            state."DigitalName${myindex}" = mynewname
        }
        if (mynewinput != state."DigitalInput${myindex}") { 
             log.info "Denkovi ${denkovi_model}:${internal_ip} mynewinput ${mynewinput} is not state.DigitalInput${myindex} ${state."DigitalInput${myindex}"}, syncing...."
             state."DigitalInput${myindex}" = mynewinput 
        }
        if (mynewcount != state."DigitalCount${myindex}") { 
             log.info "Denkovi ${denkovi_model}:${internal_ip} mynewcount ${mynewcount} is not state.DigitalCount${myindex} ${state."DigitalCount${myindex}"}, syncing...."
             state."DigitalCount${myindex}" = mynewcount 
             state."DigitalCount${myindex}Uptime" = myuptime
        }
    }

    // Parse Analog Inputs
      
    for (int myindex = 1; myindex <= 8; myindex++) {
        mynewname = payload."AnalogInput${myindex}".Name.text()
        mynewvalue = payload."AnalogInput${myindex}".Value.toInteger()
        mynewmeasure = payload."AnalogInput${myindex}".Measure.text()
        // log.debug "Denkovi ${denkovi_model}:${internal_ip} Index ${myindex} ${state."newAnalogInput${myindex}"} ${state."newAnalogCount${myindex}"} "
        if (mynewname != state."AnalogName${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewname ${mynewname} is not state.AnalogName${myindex} ${state."AnalogName${myindex}"}, syncing...."
            state."AnalogName${myindex}" = mynewname
        }
        if (mynewvalue != state."AnalogValue${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewvalue ${mynewvalue} is not state.AnalogValue${myindex} ${state."AnalogValue${myindex}"}, syncing...."
            state."AnalogValue${myindex}" = mynewvalue 
            state."AnalogValue${myindex}Uptime" = myuptime
        }
        if (mynewmeasure != state."AnalogMeasure${myindex}") { 
            log.info "Denkovi ${denkovi_model}:${internal_ip} mynewmeasure ${mynewmeasure} is not state.AnalogMeasure${myindex} ${state."AnalogMeasure${myindex}"}, syncing...."
            state."AnalogMeasure${myindex}" = mynewmeasure 
        }
    }
    
}

def syncOnOff(num) {
    if (num == null) { log.info "Denkovi ${denkovi_model}:${internal_ip} syncOnOff null found"; return }
    if (num == "0") { return "off" }
    if (num == "1") { return "on" }
    log.info "Denkovi ${denkovi_model}:${internal_ip} ${num} syncOnOff did not match"               
}

def syncChild(sname,spos) {
    String myChildDevice = "${device.id}-${sname.replaceAll("\\s","")}"
    def child = getChildDevice(myChildDevice)
    child.sendEvent(name: "switch", value: "${spos}")
}

def initialize() {
    log.info "Denkovi ${denkovi_model}:${internal_ip} Initialize..."
    unschedule(pollDenkoviSwitch)
    for (int myindex = 1; myindex <= 8; myindex++) { state."Relay${myindex}" = "off" }
    switchAllOff()
    setupControlSwitchChildren()
    runEvery1Minute(pollDenkoviSwitch)
}


def installed() {
    log.info "Denkovi ${denkovi_model}:${internal_ip} Installed....."
    initialize()
}

def uninstalled() {
    log.info "Denkovi ${denkovi_model}:${internal_ip} Uninstalled....."
    switchAllOff()
    unschedule(pollDenkoviSwitch)
    removeChildDevices(getChildDevices())
}

private removeChildDevices(delete) {
	delete.each {deleteChildDevice(it.deviceNetworkId)}
}
