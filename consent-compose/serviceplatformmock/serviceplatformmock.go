package main

import (
	"net/http"
	"fmt"
	"encoding/json"
	 "io/ioutil"
)

type Person struct {
    Cpr string `json:"cpr"`
    MunicipalityCode int `json:"code"`
}

type People struct {
    Persons []Person `json:"persons"`
}

type FetchKommuneKodeRequest struct {
    Cpr string `json:"cpr"`
}

type FetchKommuneKodeResponse struct {
    Code int `json:"kommuneKode"`
}

var (
  people *People = &People{}
)

func main() {
    fmt.Println("Reading people file")
    data, err := ioutil.ReadFile("./people.txt")
    if err != nil {
        panic(err)
    }
    json.Unmarshal(data,people)
    http.HandleFunc("/spmock/getKommuneKode", handler)
    http.HandleFunc("/getKommuneKode", handler)
    http.HandleFunc("/spmock/info", statusHandler)
    http.HandleFunc("/info", statusHandler)
    fmt.Println("Ready to serve")
    err = http.ListenAndServe(":8080",nil)
    if err != nil {
       fmt.Printf("error starting endpoint: %v\n",err)
    }
}

func statusHandler(w http.ResponseWriter, r *http.Request) {
    w.Write([]byte("OK"))
}
func handler(w http.ResponseWriter, r *http.Request) {
    request := &FetchKommuneKodeRequest{}
    body,_ := ioutil.ReadAll(r.Body)
    fmt.Println("Raw body"+string(body))
    json.Unmarshal(body,request)
    fmt.Println(request)
    code := people.lookup(request.Cpr)
    response := &FetchKommuneKodeResponse{code}
    fmt.Println(response)
    bytes,_ := json.Marshal(response)
    w.Write(bytes)
}

func (people *People) lookup(cpr string) int {
    for _,person := range people.Persons {
        if person.Cpr == cpr {
          return person.MunicipalityCode
        }
    }
    return 0;
}