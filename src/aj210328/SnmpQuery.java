package aj210328;

import java.io.IOException;
import java.util.LinkedList;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
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
	
	public static LinkedList<String> getSubtree(String rootOID, Router r) {
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
			pdu.setType(PDU.GETNEXT);
			//VariableBinding povezuje OID sa vrednoscu, moze da se doda vise OID-ova u jednom zahtevu
			pdu.add(new VariableBinding(new OID(rootOID)));
			LinkedList<String> values = new LinkedList<String>();
			while(true) {
				// slanje zahteva i prijem odgovora
				ResponseEvent response = snmp.send(pdu, target);
				PDU responsePDU = response.getResponse();
				if (responsePDU != null) {
					if(!responsePDU.getVariableBindings().get(0).getOid().startsWith(new OID(rootOID))) {
						System.out.println("obidjeno celo stablo");
						break;
					}
					values.add(responsePDU.getVariableBindings().get(0).getVariable().toString());
					pdu = new PDU();
					pdu.setType(PDU.GETNEXT);
					pdu.add(new VariableBinding(responsePDU.getVariableBindings().get(0).getOid()));
				} else {
					System.out.println("GRESKA! Nema odgovora za OID:" + rootOID);
					break;
				}
			}
			// zatvaranje veze i oslobadjanje resursa
			snmp.close();
			return values;
		} catch (IOException e) {	
			return null;
		}
	}

	
	public static void snmpSet() {
        try {
            TransportMapping<?> transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            transport.listen();
            Address targetAddress = GenericAddress.parse("udp:192.168.10.1/161");
            CommunityTarget target = new CommunityTarget();
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);
            target.setVersion(SnmpConstants.version2c);
            target.setCommunity(new OctetString("si2019"));
            PDU pdu = new PDU();
            pdu.setType(PDU.SET);
            OID oidToSet = new OID(".1.3.6.1.2.1.1.5.0");  // Replace with your OID
            OctetString variableToSet = new OctetString("R1");  // Replace with the new value
            VariableBinding vb = new VariableBinding(oidToSet, variableToSet);
            pdu.add(vb);
            // Send Set request
            ResponseEvent response = snmp.set(pdu, target);
            if (response != null && response.getResponse() != null) {
                PDU responsePDU = response.getResponse();
                if (responsePDU.getErrorStatus() == PDU.noError) {
                    System.out.println("Set request successful.");
                } else {
                    System.out.println("Set request failed. Error: " + responsePDU.getErrorStatusText());
                }
            } else {
                System.out.println("Set request timed out.");
            }

            // Close SNMP session
            snmp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
