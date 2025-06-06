# TestZarr

A Java command-line application for Zarr operations using JZarr library.

## Requirements

- Java 11 or higher
- Gradle

## Building

```bash
./gradlew build
```

## Running

```bash
./gradlew run --args="--help"
```

Or after building:

```bash
java -jar build/libs/TestZarr-1.0-SNAPSHOT.jar --help
```

## Usage:
```
Usage: testzarr [-hV] [-c=<sizeC>] [-o=<order>] [-t=<sizeT>] [-x=<sizeX>]
                [-y=<sizeY>] [-z=<sizeZ>] <filePath>
Command line tool for Zarr test file creation. You can create < 5d images by
either setting c/t/z to 0 or removing the dimensions from the order.
      <filePath>   Output file path (e.g., ./test.zarr)
  -c=<sizeC>       Number of channels (default: 3)
  -h, --help       Show this help message and exit.
  -o=<order>       Dimension order (default: TCZYX)
  -t=<sizeT>       Number of timepoints (default: 10)
  -V, --version    Print version information and exit.
  -x=<sizeX>       Width of the image (default: 512)
  -y=<sizeY>       Height of the image (default: 256)
  -z=<sizeZ>       Number of Z planes (default: 5)
```