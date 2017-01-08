# KPack
Software library connecting STEM fields to fast and powerful programming languages

NOTE: This project is a work in progress.

KPack contains 4 subdivisions, each with a specific use and a cross-platform class.
  - KMatrix: A simple syntax yet fast matrix class
  - KVar: A class for writing .kv1 and .kv2 data files.
  - KPlot: A class providing basic graphing and plotting capability.
  - KComm: A class for communicating with Arduino microcontrollers over USB.
  
KPack is written for C++, however versions of KMatrix and KVar will be made for C, Java, and Python so that data can be shared between these langages with ease. Plotting and serial communication will still need to be done in C++ with KPlot and KComm, or with other software.

KV1 files are data files that can save strings, boolean values, doubles, and matricies. KV1 files are basic text files, and are designed to be easily created, edited, and read in a text editor. KV2 files contain the same information as a KV1 file, but it sacrifices the basic text format for storage optimization by saving the data in a binary file.
