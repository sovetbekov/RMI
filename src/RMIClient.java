import java.rmi.Naming;

public class RMIClient {

    public static int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) {
                count++;
                for (int j = 2; i*j < n; j++) {
                    notPrime[i*j] = true;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/Service";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);

            while (true) {
                Integer n = service.pollElem();
                if (n == null) {
                    System.out.println("Received none!");
                    break;
                } else {
                    System.out.println("Received: " + n);
                    service.getResult(countPrimes(n));

                    System.out.println();
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}