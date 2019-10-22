/*
 ============================================================================
 Name        : Assignment2.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

int main(void) {
	int i, j, k, l;
		//get data size
		int ds = 0;
	//	printf("\n Enter the data size: ");
	//	scanf("%d", &ds);


		//get data
		int d[20] = {0};
		char poly1[20], poly2[20];
		printf("\n Enter the data as a polynomial: ");
		scanf("%s", poly1);
		i = 0;
		while(poly1[i] != '\0'){
			if(isdigit(poly1[i])){
				if(poly1[i-1] == '^'){
					int loc = poly1[i] - '0';	//get int value
					if(ds == 0){
						ds = loc+1;
						loc = 0;
					}
					else{
						loc = ds - loc - 1;
					}
					d[loc] = 1;
				}
				else{
					if(ds == 0)
						ds = 1;
					d[ds-1] = 1;
				}
			}
			if((poly1[i] == '+' && poly1[i-1] == 'x') || (poly1[i] == 'x' && poly1[i+1] == '\0')){
				if(ds == 0){
					ds = 2;
					d[0] = 1;
				}
				else{
					d[ds-2] = 1;
				}
			}
			i++;
		}
		printf("\n Data size is: %d ", ds);

		//get generator size
		int gs = 0;

		//get generator
		int g[20] = {0};
		int flag2 = 0;
		printf("\n Enter the generator as a polynomial: ");
		scanf("%s", poly2);
		i = 0;
		while(poly2[i] != '\0'){
			if(isdigit(poly2[i])){
				if(poly2[i-1] == '^'){
					int loc = poly2[i] - '0';
					if(gs == 0){
						gs = loc+1;
						loc = 0;
					}
					else{
						loc = gs - loc - 1;
					}
					g[loc] = 1;
				}
				else{
					if(gs == 0)
						gs = 1;
					g[gs-1] = 1;
				}
			}
			if((poly2[i] == '+' && poly2[i-1] == 'x') || (poly2[i] == 'x' && poly2[i+1] == '\0')){
				if(gs == 0){
					gs = 2;
					g[0] = 1;
				}
				else{
					g[gs-2] = 1;
				}
			}
			i++;
		}
		printf("\n The generator size is: %d", gs);
		int n = gs+ds-1;


		printf("\n -----------Sender side---------- ");
		printf("\n Data: ");
		for(i = 0 ; i < ds; i++){
			printf("%d", d[i]);
		}
		printf("\n Generator: ");
		for(i = 0 ; i < gs; i++){
			printf("%d", g[i]);
		}

		//append 0's
		int rs = gs-1, temp[20];
		printf("\n No. of zeros to be appended: %d ", rs);
		for(i = ds ; i < ds+rs; i++){
			d[i] = 0;
		}
		printf("\n Message after appending 0's: ");
		for(i = 0; i < ds+rs; i++){
				temp[i] = d[i];
				printf("%d", temp[i]);
		}
		printf("\n Total code word size is: %d", n);
		//XOR division
		for(i = 0 ; i< ds; i++){
			j = 0;
			k = i;
			//check whether number is divisible or not (if MSB of d is 1)
			if(temp[k] >= g[j]){
				for(j = 0 , k = i; j < gs; j++, k++)	//performing xor for generator numer of bits
				{
						if((temp[k] == 1 && g[j] == 1) || (temp[k] == 0 && g[j] == 0)){
							temp[k] = 0;
						}
						else
							temp[k] = 1;
				}
			}
		}

		//Redundancy bits
		printf("\n Redundancy bits after XOR division: ");
		int crc[15];
		for(i = 0 , j = ds; i < rs; i++, j++){
			crc[i] = temp[j];
			printf("%d", crc[i]);
		}

		int td[20];
		printf("\n Transmitted data:");
		for(i = 0 ; i < ds; i++){
			td[i] = d[i];
		}
		for(i = ds, j = 0 ; i < ds+rs; i++, j++){
			td[i] = crc[j];
		}
		for(i=0;i<ds+rs;i++)
	    {
	     	printf("%d", td[i]);
	    }
	    printf("\n ---------------------------------");
	    //changing the bits
	    char choice = 'y';
	 //   printf("\n Do you want to change any bits?");
	   // scanf("%c", &choice);
	   // printf("\n choice is: %c", choice);
	    if(choice == 'y' || choice == 'Y'){
	    	printf("\n Which bits do you want to alter (From LSB)? (Enter 0 for no change) ");
	    	scanf("%d", &i);

	    	i = (ds+rs) - i + 1;
	    	if(td[i-1] == 1){
	    		td[i-1] = 0;
	    		printf("\n Here");
	    	}
	    	else
	    		td[i-1] = 1;
	    }

	    //Reciever side:
	    printf("\n ----------Reciever side---------- ");
		printf("\n Recieved code: ");
		for(i=0;i<ds+rs;i++)
	    {
	     	printf("%d", td[i]);
	    }
		for(i = 0; i < ds+rs; i++){
				temp[i] = td[i];
		}

		//XOR division
		for( i = 0 ; i < ds; i++){
			j = 0 ;
			k = i ;
			if(temp[k] >= g[j]){
				for(j = 0, k = i ; j < gs; j++, k++){
					if((temp[k] == 1 && g[j] == 1) || (temp[k] == 0 && g[j] == 0)){
						temp[k] = 0;
					}
					else
						temp[k] = 1;
				}
			}
		}

		/*printf("\n Temp ");
		for(i=0;i<ds+rs;i++)
	    {
	     	printf("%d", temp[i]);
	    }*/

		printf("\n Remainder ");
		int rrem[15];
		for (i=ds,j=0;i<ds+rs;i++,j++)
	    {
	        rrem[j]= temp[i];
	     	printf("%d", rrem[j]);
	    }

	    int flag=0;
	    for(i=0;i<rs;i++)
	    {
	        if(rrem[i]!=0)
	        {
	            flag=1;
	        }
	    }

	    if(flag==0)
	    {
	        printf("\n Since Remainder Is 0 Hence Message Transmitted From Sender To Receiver Is Correct");
	    }
	    else
	    {
	        printf("\n Since Remainder Is Not 0 Hence Message Transmitted From Sender To Receiver Contains Error");
	    }



		return 0;
}
