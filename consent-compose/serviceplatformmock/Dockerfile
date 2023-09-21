FROM golang:1.13.8 as build
WORKDIR /go/src/spmock
COPY . .
RUN go get -d -v ./...
RUN go install -v ./...
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .

FROM scratch
WORKDIR /root/
COPY --from=build /go/src/spmock/app .
COPY --from=build /go/src/spmock/people.txt .
CMD ["./app"]