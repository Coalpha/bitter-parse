# FracCalc

- Class: "Hey make a Fractional Calculator"
- Me: "Oh that'll be easy, I'll just use the JavaScript Engine (Nashorn)"
- Class: "...You didn't really learn anything."
- Me: "That's the point"
- Class: "Do it without using Nashorn"
- Me: "Okay, I guess I'll just make a JavaScript parser in Java then"
- Me: "This is really hard"
- Joshua: "You are like a little baby."

### Usage

- Requires [`wtf_ja`](https://github.com/Coalpha/dotfiles/blob/master/bin/wtf_ja) and it's dependencies
- Basically, just install graalvm
- Run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -r`
- Compile FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java --native:bin/FracCalc`
- Compile and then run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -n -r`
