# VnCoreNLP: A Vietnamese natural language processing toolkit

VnCoreNLP is a Java NLP annotation pipeline for Vietnamese, providing rich linguistic annotations through key NLP components of *word segmentation*, *POS tagging*, *named entity recognition* (NER) and *dependency parsing*:

* **Accurate** – VnCoreNLP components obtain higher results than all previous published results on standard benchmark datasets.
* **Fast** – VnCoreNLP is fast, so it can be used for dealing with large-scale data. Also it benefits users suffering from limited computation resources (e.g. users from Vietnam).
* **Easy-to-use** – VnCoreNLP is wrapped into a single `.jar` file, so users do not have to install external dependencies. Users can run processing pipelines from either the command-line or the Java API.

The general architecture and experimental results of VnCoreNLP can be found in the following related papers:
1. Thanh Vu, Dat Quoc Nguyen, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2018**. VnCoreNLP: A Vietnamese Natural Language Processing Toolkit. *arXiv preprint*	 arXiv:1801.01331. [[.pdf]](https://arxiv.org/abs/1801.01331)
2. Dat Quoc Nguyen, Dai Quoc Nguyen, Thanh Vu, Mark Dras and Mark Johnson. **2018**. A Fast and Accurate Vietnamese Word Segmenter. In *Proceedings of the 11th International Conference on Language Resources and Evaluation*, [LREC 2018](http://lrec2018.lrec-conf.org/en/), to appear. [[.pdf]](https://arxiv.org/abs/1709.06307)
3. Dat Quoc Nguyen, Thanh Vu, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2017**. From Word Segmentation to POS Tagging for Vietnamese. In *Proceedings of the 15th Annual Workshop of the Australasian Language Technology Association*, [ALTA 2017](http://alta2017.alta.asn.au), pages 108-113. [[.pdf]](https://arxiv.org/abs/1711.04951)

Please cite paper [1] whenever VnCoreNLP is used to produce published results or incorporated into other software. If you are dealing in depth with either word segmentation or POS tagging, you are encouraged to also cite paper [2] or [3], respectively. 

VnCoreNLP is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

## Using VnCoreNLP from the command line

Assume that Java 1.8+ is already set to run in the command line or terminal (for example: adding Java to the  environment variable `path` in Windows OS); and file  `VnCoreNLP-1.0.jar` (27MB) and folder `models` (113MB) are placed in the same working folder. You can run VnCoreNLP to annotate an input raw text corpus (e.g. a collection of news content) by using following commands:

    //To perform word segmentation, POS tagging, NER and then dependency parsing
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt
    // To perform word segmentation, POS tagging and then NER
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg,pos,ner
    // To perform word segmentation and then POS tagging
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg,pos
    // To perform word segmentation
    $ java -Xmx2g -jar VnCoreNLP-1.0.jar -fin input.txt -fout output.txt -annotators wseg    

If you are looking for light-weight versions, VnCoreNLP's word segmentation and POS tagging components have also been released as independent packages [RDRsegmenter](https://github.com/datquocnguyen/RDRsegmenter) (0.3MB) and [VnMarMoT](https://github.com/datquocnguyen/VnMarMoT) (30MB), resepectively.

## Using VnCoreNLP from the API

The following code is a simple and complete example:

```java
import vn.pipeline.*;
import java.io.*;
public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {
    
        // "wseg", "pos", "ner", and "parse" refer to as word segmentation, POS tagging, NER and dependency parsing, respectively. 
        String[] annotators = {"wseg", "pos", "ner", "parse"}; 
        VnCoreNLP pipeline = new VnCoreNLP(annotators); 
    
        String str = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây"; 
        Annotation annotation = new Annotation(str); 
        pipeline.annotate(annotation); 
        
        System.out.println(annotation.toString());
        // 1    Ông                 Nc  O       4   sub 
        // 2    Nguyễn_Khắc_Chúc    Np  B-PER   1   nmod
        // 3    đang                R   O       4   adv
        // 4    làm_việc            V   O       0   root
        // ...
        
        //Write to file
        PrintStream outputPrinter = new PrintStream("output.txt", "UTF-8");
        pipeline.printToFile(annotation, outputPrinter); 
    
        // You can also get a single sentence to analyze individually 
        Sentence firstSentence = annotation.getSentences().get(0);
        System.out.println(firstSentence.toString());
    }
}
```

<img width="1552" alt="eclipse" src="https://user-images.githubusercontent.com/2412555/34871224-f412839a-f7e0-11e7-9a83-898e49647999.png">

See VnCoreNLP's open-source in folder `src` for API details. 

## Experimental results

We briefly present experimental setups and obtained results in the following subsections. See details in papers [1,2,3] above.

### Word segmentation 

* Training data: 75k manually word-segmented training sentences from the VLSP 2013 word segmentation shared task.
* Test data: 2120 test sentences from the VLSP 2013 POS tagging shared task.

<table>
  <tr>
    <td><b>Model<b></td>
    <td><b>F1 (%)</td>
    <td><b>Speed</b> (words/second)</td>
  </tr>
  <tr>
    <td>VnCoreNLP</td>
    <td><b>97.90</b></td>
    <td><b>62k</b> / _</td>
  </tr>
  <tr>
    <td>UETsegmenter</td>
    <td>97.87</td>
    <td>48k / 33k*</td>
  </tr>
   <tr>
    <td>vnTokenizer</td>
    <td>97.33</td>
    <td> _ / 5k*</td>
  </tr>
   <tr>
    <td>JVnSegmenter-Maxent</td>
    <td>97.00</td>
    <td> _ / 1k*</td>
  </tr>
   <tr>
    <td>JVnSegmenter-CRFs</td>
    <td>97.06</td>
    <td> _ / 1k*</td>
  </tr>
   <tr>
    <td>DongDu</td>
    <td>96.90</td>
    <td> _ / 17k*</td>
  </tr>
</table>

* Speed is computed on a personal computer of Intel Core i7 2.2 GHz, except when specifically mentioned. \* denotes that the speed is computed on a personal computer of   Intel Core i5 1.80 GHz.
* See paper [2] for more details.

### POS tagging 

* 27,870 sentences for training and development from the VLSP 2013 POS tagging shared task:
	* 27k sentences are used for training.
	* 870 sentences are used for development.
* Test data: 2120 test sentences from the VLSP 2013 POS tagging shared task.

<table>
    <tr>
    <td><b>Model<b></td>
    <td><b>Accuracy (%)</td>
    <td><b>Speed</td>
  </tr>
  <tr>
    <td>VnCoreNLP</td>
    <td><b>95.88</b></td>
    <td>25k</td>
  </tr>
  <tr>
    <td>RDRPOSTagger</td>
    <td>   95.11 </td>
    <td> <b>  180k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF</td>
    <td>95.06</td>
    <td> 3k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char</td>
    <td>95.40</td>
    <td> 2.5k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char</td>
    <td>95.31</td>
    <td> 1.5k</td>
  </tr>
</table>

* See paper [3] for more details.

### Named entity recognition
 * 16,861 sentences for training and development from the VLSP 2016 NER shared task:
	* 14,861 sentences are used for training.
	* 2k sentences are used for development.
* Test data: 2,831 test sentences from the VLSP 2016 NER  shared task.
* **NOTE** that the original VLSP 2016 NER data also consists of **gold** POS and chunking tags as [reconfirmed by VLSP 2016 organizers](https://drive.google.com/file/d/1XzrgPw13N4C_B6yrQy_7qIxl8Bqf7Uqi/view?usp=sharing). Also in the VLSP 2016 NER data, each word representing a full personal name are separated into syllables that constitute the word. This scheme results in an unrealistic scenario: 
	* **Gold** POS and chunking tags are NOT available in a real-world application.
	*  In the standard representation in Vietnamese word segmentation, a word segmenter outputs a full name as a word.  
* For a realistic scenario, we merge those contiguous syllables constituting a full name to form a word. Then to obtain predicted POS tags for training, developement and test sentences, we perform POS tagging by using our tagging component. The results are as follows:

<table>
    <tr>
    <td><b>Model<b></td>
    <td><b>F1</td>
    <td><b>Speed</td>
  </tr>
  <tr>
    <td>VnCoreNLP</td>
    <td>88.14</td>
    <td><b>19k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF</td>
    <td>86.48</td>
    <td> 2.8k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char</td>
    <td><b>88.28</td>
    <td> 1.8k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char</td>
    <td>87.71</td>
    <td> 1.3k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + predicted POS</td>
    <td>86.12</td>
    <td> _ </td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char + predicted POS </td>
    <td>88.06</td>
    <td> _</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char + predicted POS</td>
    <td>87.43</td>
    <td> _ </td>
  </tr>
</table>

* For computing speed of VnCoreNLP, automatically POS tagging time is also taken into account.
* Also note that on the __original__ VLSP 2016 NER data, VnCoreNLP obtains a F1 score at **93.2%**, which is higher than all previous published results.
* See paper [1] for more details.

### Dependency parsing

* We use the Vietnamese dependency treebank [VnDT](http://vndp.sourceforge.net)  consisting of 10,200 sentences. We use the last 1020 sentences of VnDT for test while the remaining sentences are used for training.

<table>
  <tr>
    <th colspan="2"><b>Model</b></th>
    <th> <b>LAS</b> (%)</th>
    <th><b>UAS</b> (%)</th>
    <th><b>Speed</th>
  </tr>
  <tr>
    <td rowspan="5">Gold POS</td>
    <td>VnCoreNLP</td>
    <td><b>73.39</td>
    <td>79.02</td>
    <td>_</td>
  </tr>
  <tr>
    <td>BIST-bmstparser</td>
    <td>73.17</td>
    <td><b>79.39</td>
    <td>_</td>
  </tr>
  <tr>
    <td>BIST-barchybrid</td>
    <td>72.53</td>
    <td>79.33</td>
    <td>_</td>
  </tr>
  <tr>
    <td>MSTparser</td>
    <td>70.29</td>
    <td>76.47</td>
    <td>_</td>
  </tr>
  <tr>
    <td>MaltParser</td>
    <td>69.10</td>
    <td>74.91</td>
    <td>_</td>
  </tr>
  <tr>
    <td rowspan="2">Predicted POS</td>
    <td>VnCoreNLP</td>
    <td><b>70.23</td>
    <td>76.93</td>
    <td><b>8k</td>
  </tr>
  <tr>
    <td>jPTDP</td>
    <td>69.49</td>
    <td><b>77.68</td>
    <td>700</td>
  </tr>
</table>

* For computing speed of VnCoreNLP, automatically POS tagging time is also taken into account.
* See paper [1] for more details.
