# KPack

*This project is no longer maintained. It has been replaced by projects Grant-Giesbrecht/gstd, KTable, KV_Spec*

Software library connecting STEM fields to fast and powerful programming languages

NOTE: This project is a work in progress.

KPack contains 4 subdivisions, each with a specific job. All are cross-platform.
  - KMatrix: A simple syntax yet fast matrix class
  - KVar: A class for writing .KV1, .KV2, .KV3, and .KV4 data files.
  - KPlot: A class providing graphing and plotting capability.
  - KComm: A class for communicating with Arduino microcontrollers over USB.
  
KPack is written for C++, however versions of KMatrix and KVar will be made for C, Java, and Python so that data can be shared between these langages with ease. Plotting and serial communication will still need to be done in C++ with KPlot and KComm, or with other software.

KV1 files are data files that can save strings, boolean values, doubles, and matricies. KV1 files are basic text files, and are designed to be easily created, edited, and read in a text editor. They can also contain descriptors for each variable saved in the file, and a header comment that gives a general description of the file. KV2 files contain the same information as a KV1 file, but they sacrifice the ease of use of the text format for storage optimization by saving the data in a binary file. KV3 files are AES encrypted versions of KV2 files. KV4 files simply contain any number of KV1, 2, 3, or 4 files in a directory based fashion. The primary use of this is to allow KV file users to store their data in whichever is easiest for them - saving data in multiple files may be found desirable due to the multiple headers and combination of encrypted and non-encrypted information. A KV4 file is constructed from a zipped folder containing KV files.

It should also be noted that a command line program exists that aides in the use of KPack. The Command Line Calculator (CLC) is available via an MIT liscence that serves as a programmable calculator and interface for working with KPack by the command line. It can read, save, and convert between the various KV file formats (.KV1, .KV2, .KV3, and .KV4). It can also plot data and perform matrix manipulation on KMatricies. CLC serves as an easy way to view and manipulate the contents of .KV\* files and KPack supported variables (KMatricies, booleans, doubles, and strings). For more information and downloads, check out CLC on GitHub.
