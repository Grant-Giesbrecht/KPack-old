# KPack
Software library connecting STEM fields to fast and powerful programming languages

NOTE: This project is a work in progress.

KPack contains 4 subdivisions, each with a specific use and a cross-platform class. It also includes one command line program (See KCalculator below)
  - KMatrix: A simple syntax yet fast matrix class
  - KVar: A class for writing .KV1, .KV2, and .KV3 data files.
  - KPlot: A class providing basic graphing and plotting capability.
  - KComm: A class for communicating with Arduino microcontrollers over USB.
  
KPack is written for C++, however versions of KMatrix and KVar will be made for C, Java, and Python so that data can be shared between these langages with ease. Plotting and serial communication will still need to be done in C++ with KPlot and KComm, or with other software.

KV1 files are data files that can save strings, boolean values, doubles, and matricies. KV1 files are basic text files, and are designed to be easily created, edited, and read in a text editor. They can also contain descriptors for each variable saved in the file, and a header comment that gives a general description of the file. KV2 files contain the same information as a KV1 file, but they sacrifice the ease of use of the text format for storage optimization by saving the data in a binary file. KV3 files are AES encrypted versions of KV2 files.

 KCalculator is a command line program that serves as a programmable calculator and interface for working with KPack modules by the command line. It can read, save, and convert between the various KV file formats (.KV1, .KV2, and .KV3). It can also plot data and perform matrix manipulation on KMatricies. KCalculator (KC is name of binary) serves as an easy way to view and manipulate the contents of .KV\* files and KPack supported variables (KMatricies, booleans, doubles, and strings).
