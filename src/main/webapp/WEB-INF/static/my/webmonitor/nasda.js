var InterfacePropertyMap = {
	"SNMP":{"SNMP_HOST_NAME" :{"name":"hostName", "title": "Host Name","type":"text","required":true},
		"SNMP_ADLHN_1" :{"name":"additionalHostNames", "title": "AdditionalHostNames","type":"text","required":false},
		"SNMP_PORT" :{"name":"port", "title": "Port","type":"text","required":true},
		"SNMP_NSSM_7" :{"name":"neSurveillanceSupervisionMethod", "title": "NE Surveillance Supervision Method","type":"select","option":{"0":"ICMP","1":"SNMP","2":"ICMP followed by SNMP"}},
		"SNMP_VER" :{"name":"snmpVersion", "title": "SNMP Verion","type":"select","option":{"0":"v1","1":"v2c","2":"v3"}}},
	"NE3SWS":{"NE3SWS_HOST_NAME" :{"name":"hostName", "title": "Host Name","type":"text","required":true},
		"NE3SWS_SECURITY_MODE" :{"name":"securityMode", "title": "Security Mode","type":"select","option":{"0":"No TLS","1":"TLS"}},
		"NE3SWS_HTTP_PORT" :{"name":"httpPort","title": "Http Port","type":"text","required":true},
		"NE_3_SWS_HTTPS_PT" :{"name":"httpsPort", "title": "Https Port","type":"text","required":true},
		"NE3SWS_IP_VER" :{"name":"ipVersion", "title": "Ip Verion","type":"select","option":{"0":"IPv4","1":"IPv6"}}},
	"SSH":{"SSH_HOST_NAME" :{"name":"hostName", "title": "Host Name","type":"text","required":true},
		"SSH_PORT" :{"name":"port", "title": "Port","type":"text","required":true}},
	"FTP":{"FTP_HOST_NAME" :{"name":"hostName", "title": "Host Name","type":"text","required":true},
		"FTP_PORT" :{"name":"port", "title": "Port","type":"text","required":true},
		"FTP_SECURITY_MODE" :{"name":"securityMode", "title": "Security Mode","type":"select","option":{"0":"FTP","1":"SFTP"}}},
	"HTTP":{"HTTP_HOST_NAME" :{"name":"hostName", "title": "Host Name","type":"text","required":true},
		"HTTP_PORT" :{"name":"port", "title": "Port","type":"text","required":true},
		"HTTP_SECURITY_MODE" :{"name":"securityMode", "title": "Security Mode","type":"select","option":{"0":"HTTP","1":"HTTPS"}}}
};


