/**
 *  Deploy_Test
 *
 *  Copyright 2016 Nikita
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Deploy_Test",
    namespace: "NilsBohr",
    author: "Nikita",
    description: "Testing SmartApp to do various tests on SmartApp deployment",
    category: "",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Title") {
        input "door_sensors", "capability.contactSensor", title:"Which Open/Close Sensors?", multiple:true, required:false
		input "switches", "capability.switch", title: "Which Switches?", multiple: true, required: false
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	door_sensors.each { device -> 
    	device.supportedAttributes.each { attr ->
            log.debug "subscribe $device $attr"
        	subscribe(device, attr.toString(), eventHandler)
        }
    }
}

def eventHandler(evt) {
	log.debug "Event ${evt.name} ${evt.value}"
    
    if (evt.value == "open") {
    	switches.each { 
        	it.on()
            log.debug "turn $it on"
            log.debug "Habracadabra on"
        }
    } else {
    	if (evt.value == "closed") {
    		switches.each { 
            	it.off()
                log.debug "turn $it off"
                log.debug "Habracadabra off"
            }
        }
    }
}