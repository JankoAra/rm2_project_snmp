package aj210328;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpQuery {

	public static String getOID(String oid, Router r) {
		try {
			// Create SNMP4J objects
			TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);
			transport.listen();

			// Create target
			String targetAddress = "udp:" + r.getIpAddress() + "/161";
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(Router.COMMUNITY_STRING)); // SNMP community string
			target.setAddress(GenericAddress.parse(targetAddress));
			target.setRetries(2);
			target.setTimeout(1500);
			target.setVersion(SnmpConstants.version2c);

			// Create PDU
			PDU pdu = new PDU();
			pdu.setType(PDU.GET);
			pdu.add(new VariableBinding(new OID(oid)));

			// Send the request
			ResponseEvent response = snmp.send(pdu, target);
			PDU responsePDU = response.getResponse();

			// Process the response
			if (responsePDU != null) {
				// System.out.println("Received response: " +
				// responsePDU.getVariableBindings().get(0).getVariable());
			} else {
				System.out.println("No response received. oid:" + oid);
				return null;
			}

			// Close resources
			snmp.close();

			return responsePDU.getVariableBindings().get(0).getVariable().toString();
		} catch (IOException e) {
			return null;
		}
	}

}
