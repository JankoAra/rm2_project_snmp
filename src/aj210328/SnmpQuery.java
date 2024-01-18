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
			// otvaranje konekcije za SNMP preko UDP protokola
			TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);
			snmp.listen();

			// postavljanje odredista (rutera)
			String targetAddress = "udp:" + r.getIpAddress() + "/161";	//port 161
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(Router.COMMUNITY_STRING)); // SNMP community string
			target.setAddress(GenericAddress.parse(targetAddress));
			target.setRetries(2);
			target.setTimeout(1500);
			target.setVersion(SnmpConstants.version2c);

			// stvaranje zahteva
			PDU pdu = new PDU();
			pdu.setType(PDU.GET);
			//VariableBinding povezuje OID sa vrednoscu, moze da se doda vise OID-ova u jednom zahtevu
			pdu.add(new VariableBinding(new OID(oid)));

			// slanje zahteva i prijem odgovora
			ResponseEvent response = snmp.send(pdu, target);
			PDU responsePDU = response.getResponse();
			String oidValue = null;
			if (responsePDU != null) {
				//responsePDU.getVariableBindings vraca listu svih dodatih VB
				//ovde je dodat samo jedan VB pa uzimamo prvi iz liste
				//VB.getOid() vraca OID
				//VB.getVariable() vraca vrednost tog OID-a
				oidValue = responsePDU.getVariableBindings().get(0).getVariable().toString();
			} else {
				System.out.println("GRESKA! Nema odgovora za OID:" + oid);
			}

			// zatvaranje veze i oslobadjanje resursa
			snmp.close();
			return oidValue;
		} catch (IOException e) {	
			return null;
		}
	}

}
