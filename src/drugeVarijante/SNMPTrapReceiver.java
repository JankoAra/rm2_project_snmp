package drugeVarijante;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SNMPTrapReceiver {
	public static void main(String[] args) {
		try {
			
			Snmp snmp = new Snmp(new DefaultUdpTransportMapping(new UdpAddress("192.168.122.1/2000")));

			// Set up the trap receiver
			snmp.addCommandResponder(new CommandResponder() {
				@Override
				public synchronized void processPdu(CommandResponderEvent event) {
					PDU pdu = event.getPDU();
					Address senderAddress = event.getPeerAddress();

                    System.out.println("Received trap from " + senderAddress + ", Request ID: " + pdu.getRequestID());

					for(VariableBinding vb:pdu.getVariableBindings()) {
						//System.out.println(vb.toString());
						System.out.println("OID:"+vb.getOid().toString() +", value:"+vb.getVariable().toString());
					}
				}
			});

			// Start listening for traps
			snmp.listen();

			System.out.println("SNMPv2c Trap Receiver listening on UDP/2000...");

			// Wait for traps
			while (true) {
				Thread.sleep(1000); // Adjust as needed
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
