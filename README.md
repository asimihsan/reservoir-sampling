# reservoir-sampling

## Introduction

Take a fair sample of fixed size over a large stream of unknown size.

Currently implements Algorithm R by Alan Waterman, the simplest but most wasteful reservoir algorithm. Requires a
random number for each element seen in the stream.