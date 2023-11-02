#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <sys/wait.h>

typedef struct 
{
    int option;
    int *arr1;
    int *arr2;
    int size;
    double realNum;
} Request;

void error(const char *msg)
{
    perror(msg);
    exit(0);
}
int readSize(const char* message)
{
     int s;
     printf("%s\n",message);
	 do 
	 {
		 scanf("%d",&s);
		 if (s<=0) printf("Size %d must be positive. Please enter size again: ",s);
	 } while (s <= 0);
	 return s;
}
void ReadMatrix (int* M, int S)
{
    int i;
    printf("Enter %d elements for array\n",S);
    for (i = 0; i < S; i++)
    {
        printf ("Enter Value for Position %d: ", i + 1);
        scanf("%d",&M[i]);     
    }
}
int *CreateMatrix (int S)
{
    int *T;
    T = (int *) calloc (S , sizeof (int));
    if (T == NULL)   
    {
        printf("Not enough memory...\n");
        exit (1);
    }
    return T;
}
void printArray(const double* array,int size,FILE* out)
{
    int i;
    for(int i=0;i<size;i++) fprintf(out,"Value for Position %d: %f\n",i+1,array[i]);
}
int initRequest (Request* req)
{
    req->size = readSize("Please enter the size (n) of arrays: ");
    req->arr1 = CreateMatrix(req->size);
    ReadMatrix(req->arr1,req->size);
    req->arr2 = CreateMatrix(req->size);
    ReadMatrix(req->arr2,req->size);
    if(req->option == 3) 
    {
        puts("Enter the real number (r):");
        scanf("%lf",&req->realNum);
    }
    else req->realNum = 0;
    return sizeof(*req);
}
void serialize_request(Request *req, char *buffer, int *len) {
    int pos = 0;
    memcpy(buffer + pos, &(req->option), sizeof(int));
    pos += sizeof(int);
    memcpy(buffer + pos, &(req->size), sizeof(int));
    pos += sizeof(int);
    memcpy(buffer + pos, &(req->realNum), sizeof(double));
    pos += sizeof(double);
    memcpy(buffer + pos, req->arr1, req->size * sizeof(int));
    pos += req->size * sizeof(int);
    memcpy(buffer + pos, req->arr2, req->size * sizeof(int));
    pos += req->size * sizeof(int);
    *len = pos;
}
void Menu(Request* request,int sockfd)
{
    int requestLen;
    char *buffer;
	do
	{
        printf ("            Μενού Επιλογών\n");
	    printf ("1. Το εσωτερικό γινόμενο των δύο διανυσμάτων Χ ∙ Υ\n");
        printf ("2. Τη μέση τιμή κάθε διανύσματος: ΕΧ, ΕΥ\n");
        printf ("3. Το γινόμενο r*(Χ+Y)\n");
	    printf("4. Έξοδος\n");
	    printf ("\nΕισάγετε μια επιλογή (1,2,3,4): ");
        scanf("%d",&request->option);  
	    switch(request->option)
	    {
		    case 1:
                requestLen= initRequest(request);
                buffer = (char*)malloc(requestLen); 
                serialize_request(request, buffer, &requestLen);
                free(request->arr1) ;free(request->arr2);
                send(sockfd, &requestLen, sizeof(int), 0);
                send(sockfd, buffer, requestLen, 0);
                free (buffer);
                int dotProductRes;
                recv(sockfd,&dotProductRes,sizeof(int),0);
                printf("Dot product: %d\n",dotProductRes);      
	        break;
            case 2:
                requestLen= initRequest(request);
                buffer = (char*)malloc(requestLen); 
                serialize_request(request, buffer, &requestLen);
                free(request->arr1) ;free(request->arr2);
                send(sockfd, &requestLen, sizeof(int), 0);
                send(sockfd, buffer, requestLen, 0);
                free (buffer);
                double avgRes1,avgRes2;
                recv(sockfd,&avgRes1,sizeof(double),0);
                recv(sockfd,&avgRes2,sizeof(double),0);
                printf("Average 1: %f\nAverage 2: %f",avgRes1,avgRes2);               
	        break;
            case 3:
                requestLen= initRequest(request);
                buffer = (char*)malloc(requestLen); 
                serialize_request(request, buffer, &requestLen);
                free(request->arr1);free(request->arr2);
                send(sockfd, &requestLen, sizeof(int), 0);
                send(sockfd, buffer, requestLen, 0);
                free (buffer);
                double* resultArray = (double*)calloc(request->size,sizeof(double));
                recv(sockfd,resultArray,sizeof(double)*request->size,0);
                printArray(resultArray,request->size,stdout);
                free(resultArray); 
	        break;
		    case 4:
		        printf("Αντίο\n"); 
		    break;	
		    default:
                printf("Μη έγκυρη επιλογή\n"); 
		    break;
	    }
  } while (request->option != 4);
}
int main(int argc, char *argv[])
{
    int sockfd, portno, n, t, done,sent,total_sent;
    struct sockaddr_in serv_addr;
    struct hostent *server;
    char str[100];
    Request clientInput;
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");

    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }

    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr,(char *)&serv_addr.sin_addr.s_addr,server->h_length);
    serv_addr.sin_port = htons(portno);

    printf("Trying to connect...\n");

    if (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) error("ERROR connecting");

    printf("Connected.\n");
    Menu(&clientInput,sockfd);
    close(sockfd);
    return 0;
}

