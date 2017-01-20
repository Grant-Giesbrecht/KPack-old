#For windows

CC = g++ -std=c++11
OBJS = kc_aux.o KMatrix.o stdutil.o string_manip.o CLIK.o

all: kc.cpp $(OBJS)
	$(CC) -o kc kc.cpp $(OBJS)
	
kc_aux.o: kc_aux.cpp
	$(CC) -c kc_aux.cpp
	
KMatrix.o: KMatrix.cpp
	$(CC) -c KMatrix.cpp
	
stdutil.o: stdutil.cpp
	$(CC) -c stdutil.cpp
	
string_manip.o: string_manip.cpp
	$(CC) -c string_manip.cpp

CLIK.o: CLIK.cpp
	$(CC) -c CLIK.cpp
	
clean:
	@del $(OBJS) > nul 2> nul
	@del kc.exe > nul 2> nul
	
fresh: clean all
	