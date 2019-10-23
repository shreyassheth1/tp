#include<bits/stdc++.h>
#include<pcap.h>
#include<net/ethernet.h>
#include<netinet/ip.h>
#include<netinet/in.h>
#include<netinet/tcp.h>
#include<arpa/inet.h>
#define pb push_back
using namespace std;

vector<string> v;

int tcp = 0, udp = 0, distIp = 0;

void packetHandler(u_char *userData, const struct pcap_pkthdr* pkthdr, const u_char* packet){
	const struct ether_header* ethernetHeader;
	const struct ip* ipHeader;
	const struct tcphdr* tcpHeader;
	const struct udphdr* udpHeader;
	char sourceIp[INET_ADDRSTRLEN];
	char destIp[INET_ADDRSTRLEN];
	// u_int sourcePort, destPort;

	ethernetHeader = (struct ether_header*)packet;

	if(ntohs(ethernetHeader->ether_type) == ETHERTYPE_IP){
		ipHeader = (struct ip*)(packet + sizeof(struct ether_header));
		inet_ntop(AF_INET, &(ipHeader->ip_src), sourceIp, INET_ADDRSTRLEN);
		inet_ntop(AF_INET, &(ipHeader->ip_dst), destIp, INET_ADDRSTRLEN);
		if(find(v.begin(), v.end(), sourceIp) == v.end()){
			v.pb(sourceIp);
			distIp++;
		}
		if(find(v.begin(), v.end(), destIp) == v.end()){
			v.pb(destIp);
			distIp++;
		}
		if(ipHeader->ip_p == IPPROTO_TCP){
			tcpHeader = (tcphdr*)(packet + sizeof(struct ether_header) + sizeof(struct ip));
			// sourcePort = ntohs(tcpHeader->source);
			// destPort = ntohs(tcpHeader->dest);
			tcp++;
		}
		
		if(ipHeader->ip_p == IPPROTO_UDP){
			udpHeader = (udphdr*)(packet + sizeof(struct ether_header) + sizeof(struct ip));
			// sourcePort = ntohs(udpHeader->source);
			// destPort = ntohs(udpHeader->dest);
			udp++;
		}
	}
}

int main(int argc, char* argv[]){
	
	pcap_t *desc;
	char errbuf[PCAP_ERRBUF_SIZE];

	desc = pcap_open_offline(argv[1], errbuf); 
	if(desc == NULL){
		cout << "pcap_open failed" << endl;
		exit(1);
	}

	//Start packet processing
	if(pcap_loop(desc, 0, packetHandler, NULL)<0){
		cout << "pcap_loop failed" << pcap_geterr(desc) << endl;
		exit(1);
	}
	cout << "Total number of TCP packets : " << tcp << endl;
	cout << "Total number of UDP packets : " << udp << endl;
	cout << "Total number of IP addresses : " << distIp << endl;
	return 0;
}
